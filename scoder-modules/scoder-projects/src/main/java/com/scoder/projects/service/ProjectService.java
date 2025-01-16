package com.scoder.projects.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scoder.projects.domain.Project;
import com.scoder.projects.domain.ProjectUser;

import java.util.List;

/**
 * Service interface for managing projects and their user associations.
 * Provides methods for adding, joining, deleting projects, and retrieving related data.
 *
 * @author Shawn Cui
 */
public interface ProjectService extends IService<Project> {

    /**
     * Adds a new project to the system.
     *
     * @param project The project details.
     * @return The number of rows affected in the database.
     */
    int addProject(Project project);

    /**
     * Associates the current user with a specific project.
     *
     * @param projectId The ID of the project the user wants to join.
     * @return The number of rows affected in the database.
     */
    int joinProject(Long projectId);

    /**
     * Deletes a project and removes its user associations.
     *
     * @param projectId The ID of the project to delete.
     * @return The number of rows affected in the database.
     */
    int deleteProject(Long projectId);

    /**
     * Retrieves a list of users associated with a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of users linked to the specified project.
     */
    List<ProjectUser> getProjectUsers(Long projectId);

    /**
     * Retrieves a list of projects a specific user is associated with.
     *
     * @param userId The ID of the user.
     * @return A list of projects linked to the specified user.
     */
    List<ProjectUser> getUserProjects(Long userId);
}