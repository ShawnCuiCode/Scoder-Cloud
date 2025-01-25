package com.scoder.projects.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.common.security.service.TokenService;
import com.scoder.im.api.RemoteIMService;
import com.scoder.im.api.domain.Group;
import com.scoder.projects.domain.Team;
import com.scoder.projects.domain.TeamUser;
import com.scoder.projects.domain.vos.TeamVo;
import com.scoder.projects.mapper.TeamMapper;
import com.scoder.projects.mapper.TeamUserMapper;
import com.scoder.projects.service.TeamService;
import com.scoder.user.api.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamUserMapper teamUserMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RemoteIMService remoteIMService;

    /**
     * Creates a new team and associates the current user with it.
     *
     * @param team The team details to be created.
     * @return The number of rows affected in the database.
     */
    @Override
    public int postTeam(Team team) {
        Long teamId = IdWorker.getId();
        Long userId = tokenService.getLoginUser().getUserId();
        int teamUserInsert = teamUserMapper.insert(new TeamUser(team.getProjectId(), teamId, userId));
        int teamInsert = teamMapper.insert(team.setTeamId(teamId).setCreateBy(userId).setCreateTime(new Date()));
        Group group = new Group();
        group.setName(team.getTeam());
        group.setAvatar(team.getAvatar());
        group.setDescription(team.getDescription());
        List<Long> memberIds = new ArrayList<>();
        memberIds.add(userId);
        group.setCreateBy(userId);
        group.setMemberIds(memberIds);
        group.setTeamId(teamId);
        AjaxResult<Group> groupCreate = remoteIMService.createGroup(group);
        if (groupCreate.getCode() == 200 && teamInsert > 0 && teamUserInsert > 0) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Allows the current user to join an existing team.
     *
     * @param teamId The ID of the team the user wants to join.
     * @return The number of rows affected in the database.
     */
    @Override
    public int joinTeam(Long teamId) {
        Long userId = tokenService.getLoginUser().getUserId();
        remoteIMService.joinTeam(teamId, userId);
        return teamUserMapper.insert(new TeamUser(teamMapper.selectById(teamId).getProjectId(), teamId, userId));
    }

    /**
     * Retrieves all users associated with a specific team.
     *
     * @param teamId The ID of the team.
     * @return A list of users who are part of the team.
     */
    @Override
    public List<TeamUser> getTeamUsers(Long teamId) {
        return teamUserMapper.selectList(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teamId));
    }

    /**
     * Retrieves all teams that a specific user is part of.
     *
     * @param userId The ID of the user.
     * @return A list of teams the user is associated with.
     */
    @Override
    public List<TeamUser> getUserTeams(Long userId) {
        return teamUserMapper.selectList(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getUserId, userId));
    }

    /**
     * Deletes a team based on its ID.
     *
     * @param teamId The ID of the team to delete.
     * @return The number of rows affected in the database.
     */
    @Override
    public int deleteTeam(Long teamId) {
        return teamMapper.deleteById(teamId);
    }

    /**
     * Retrieves a list of teams related to a project and ranks them based on compatibility with the user.
     *
     * @param projectId The project ID to filter teams by.
     * @param userId    The user ID to compute compatibility.
     * @return A ranked list of team details with compatibility scores.
     */
    @Override
    public List<TeamVo> getTeamListByUser(Long projectId, Long userId) {
        // Get user-specific data such as skills, experience, and goals
        SysUser sysUser = tokenService.getLoginUser().getSysUser();

        // Fetch teams related to the project
        List<Team> teams = teamMapper.selectList(
                new LambdaQueryWrapper<Team>().eq(Team::getProjectId, projectId)
        );

        if (CollectionUtil.isEmpty(teams)) {
            return new ArrayList<>();
        }

        // Transform team data into JSON for R script processing
        List<Map<String, Object>> teamList = teams.stream().map(team -> {
            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("Team", team.getTeam());
            teamMap.put("TeamId", team.getTeamId());
            teamMap.put("Experience", team.getExperience());
            teamMap.put("Goals", team.getGoal());
            teamMap.put("Skills", team.getSkills());
            teamMap.put("userSkills", sysUser.getSkills());
            teamMap.put("userId", sysUser.getUserId());
            teamMap.put("userExperience", sysUser.getExperience());
            teamMap.put("userGoals", sysUser.getGoal());
            return teamMap;
        }).collect(Collectors.toList());
        String matchJson = JSON.toJSONString(teamList);
        log.info("Generated JSON for R Script: " + matchJson);
        // Call the R script to compute compatibility scores
        String result = calculateTeamMatches(matchJson);
        log.info("R Script Output: " + result);

        // Parse the R script's output
        List<TeamVo> teamVoList = JSON.parseArray(result, TeamVo.class);
        teamVoList.forEach(teamVo -> {
            Team team = teamMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getTeam, teamVo.getTeam()));
            BeanUtils.copyProperties(team, teamVo);
//            teamVo.setRadarImg("http://127.0.0.1:9204/statics/" + teamVo.getRadarImg());
            teamVo.setRadarImg("http://scoder.co.uk:9204/statics/" + teamVo.getRadarImg());
            teamVo.setRadarImg(teamVo.getRadarImg().replace("/images", ""));
            TeamUser teamUser = teamUserMapper.selectOne(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teamVo.getTeamId()).eq(TeamUser::getUserId, userId));
            teamVo.setIsJoined(teamUser == null ? 0 : 1);
        });
        return teamVoList;
    }

    @Override
    public int updateTeam(Team team) {
        Group group = new Group();
        group.setName(team.getTeam());
        group.setAvatar(team.getAvatar());
        group.setDescription(team.getDescription());
        group.setTeamId(team.getTeamId());
        remoteIMService.updateTeam(group);
        return teamMapper.updateById(team);
    }

    /**
     * Executes an R script to calculate team compatibility scores based on user and team data.
     *
     * @param matchJson The match' details in JSON format.
     * @return The output of the R script as a String.
     */
    private String calculateTeamMatches(String matchJson) {
//        String rScriptPath = "/Users/lanzy/IdeaProjects/Scoder-Cloud/scoder-modules/scoder-projects/src/main/java/com/scoder/projects/service/impl/Recommend.R";
        String rScriptPath = "/home/scoder/Recommend.R";
        String result = "";
        try {
            String[] cmd = {
                    "Rscript",
                    rScriptPath,
                    matchJson
            };
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                result = output.toString();
            } else {
                log.error("R script execution failed with exit code: {}", exitCode);
                result = "Error: R script execution failed.";
            }
        } catch (Exception e) {
            log.error("Error while executing R script: ", e);
            result = "Error: " + e.getMessage();
        }

        return result;
    }
}