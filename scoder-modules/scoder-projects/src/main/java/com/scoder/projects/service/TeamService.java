package com.scoder.projects.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scoder.projects.domain.Team;
import com.scoder.projects.domain.TeamUser;
import com.scoder.projects.domain.vos.TeamVo;

import java.util.List;

/**
 * Service interface for managing teams and their user associations.
 * Provides methods for creating, joining, retrieving, and deleting teams,
 * as well as handling user-team relationships.
 *
 * @author Shawn Cui
 */
public interface TeamService extends IService<Team> {

    /**
     * Creates a new team and associates it with a project and the creator.
     *
     * @param team The team details to be created.
     * @return The number of rows affected in the database.
     */
    int postTeam(Team team);

    /**
     * Allows a user to join a specific team.
     *
     * @param teamId The ID of the team to join.
     * @return The number of rows affected in the database.
     */
    int joinTeam(Long teamId);

    /**
     * Retrieves a list of users associated with a specific team.
     *
     * @param teamId The ID of the team.
     * @return A list of users linked to the specified team.
     */
    List<TeamUser> getTeamUsers(Long teamId);

    /**
     * Retrieves a list of teams a specific user is associated with.
     *
     * @param userId The ID of the user.
     * @return A list of teams linked to the specified user.
     */
    List<TeamUser> getUserTeams(Long userId);

    /**
     * Deletes a team and removes its associations with users.
     *
     * @param teamId The ID of the team to delete.
     * @return The number of rows affected in the database.
     */
    int deleteTeam(Long teamId);

    /**
     * Retrieves a list of teams associated with a specific project and user.
     * Also calculates match scores for skills, experience, and goals.
     *
     * @param projectId The ID of the project.
     * @param userId    The ID of the user for whom match scores are calculated.
     * @return A list of TeamVo objects containing detailed team and match information.
     */
    List<TeamVo> getTeamListByUser(Long projectId, Long userId);

    int updateTeam(Team team);
}