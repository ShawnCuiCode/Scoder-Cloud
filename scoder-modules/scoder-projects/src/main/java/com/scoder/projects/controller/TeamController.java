package com.scoder.projects.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scoder.common.core.web.controller.BaseController;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.common.security.service.TokenService;
import com.scoder.projects.domain.Team;
import com.scoder.projects.domain.TeamUser;
import com.scoder.projects.domain.vos.TeamVo;
import com.scoder.projects.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles HTTP requests related to teams.
 * Provides endpoints for managing team creation, retrieval, updates, deletion, and user interactions with teams.
 *
 * @author Shawn Cui
 */
@Slf4j
@RestController
@RequestMapping("/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TokenService tokenService;

    /**
     * Creates a new team.
     *
     * @param team The team details provided in the request body.
     * @return A result indicating whether the operation was successful.
     */
    @PostMapping
    public AjaxResult addTeam(@Validated @RequestBody Team team) {
        return toAjax(teamService.postTeam(team));
    }

    /**
     * Retrieves details of a specific team by its ID.
     *
     * @param teamId The ID of the team to retrieve.
     * @return The details of the specified team.
     */
    @GetMapping("/{teamId}")
    public AjaxResult<Team> getTeamById(@PathVariable("teamId") Long teamId) {
        return AjaxResult.success(teamService.getById(teamId));
    }

    /**
     * Retrieves all teams associated with a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of teams related to the specified project.
     */
    @GetMapping("/project/{projectId}")
    public AjaxResult<List<Team>> getTeamsByProjectId(@PathVariable("projectId") Long projectId) {
        return AjaxResult.success(teamService.list(new LambdaQueryWrapper<Team>().eq(Team::getProjectId, projectId)));
    }

    /**
     * Retrieves a list of teams for a specific project and user, ranked by compatibility.
     *
     * @param projectId The ID of the project.
     * @param userId    The ID of the user.
     * @return A list of teams ranked by compatibility for the user.
     */
    @GetMapping("/getTeamListByUser")
    public AjaxResult<List<TeamVo>> getTeamListByUser(@RequestParam("projectId") Long projectId,
                                                      @RequestParam("userId") Long userId) {
        return AjaxResult.success(teamService.getTeamListByUser(projectId, userId));
    }

    /**
     * Updates the details of an existing team.
     *
     * @param team The updated team details provided in the request body.
     * @return A result indicating whether the update operation was successful.
     */
    @PutMapping
    public AjaxResult updateTeam(@Validated @RequestBody Team team) {
        log.info("Received team: {}", team);
        return toAjax(teamService.updateTeam(team));
    }

    /**
     * Deletes a specific team by its ID.
     *
     * @param teamId The ID of the team to delete.
     * @return A result indicating whether the delete operation was successful.
     */
    @DeleteMapping("/{teamId}")
    public AjaxResult deleteTeamById(@PathVariable("teamId") Long teamId) {
        return toAjax(teamService.removeById(teamId));
    }

    /**
     * Allows the current user to join a specific team.
     *
     * @param teamId The ID of the team to join.
     * @return A result indicating whether the join operation was successful.
     */
    @PostMapping("/{teamId}/join")
    public AjaxResult joinTeam(@PathVariable("teamId") Long teamId) {
        return toAjax(teamService.joinTeam(teamId));
    }

    /**
     * Retrieves all users associated with a specific team.
     *
     * @param teamId The ID of the team.
     * @return A list of users who are part of the specified team.
     */
    @GetMapping("/{teamId}/users")
    public AjaxResult<List<TeamUser>> getTeamUsers(@PathVariable("teamId") Long teamId) {
        return AjaxResult.success(teamService.getTeamUsers(teamId));
    }

    @GetMapping("/getMyTeamList")
    public AjaxResult<List<Team>> getMyTeamList() {
        return AjaxResult.success(teamService.list(new LambdaQueryWrapper<Team>().eq(Team::getCreateBy, tokenService.getLoginUser().getUserId())));
    }

    /**
     * Retrieves all teams that a specific user is part of.
     *
     * @param userId The ID of the user.
     * @return A list of teams the user is associated with.
     */
    @GetMapping("/getUserTeams/{userId}")
    public AjaxResult<List<TeamUser>> getUserTeams(@PathVariable("userId") Long userId) {
        return AjaxResult.success(teamService.getUserTeams(userId));
    }
}