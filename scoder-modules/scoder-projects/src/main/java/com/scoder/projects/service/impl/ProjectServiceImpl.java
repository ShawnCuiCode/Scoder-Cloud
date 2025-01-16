package com.scoder.projects.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scoder.common.security.service.TokenService;
import com.scoder.projects.domain.Project;
import com.scoder.projects.domain.ProjectUser;
import com.scoder.projects.mapper.ProjectMapper;
import com.scoder.projects.mapper.ProjectUserMapper;
import com.scoder.projects.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the ProjectService interface.
 * This service provides operations for managing projects and their associations with users.
 * <p>
 * It interacts with the database using MyBatis Plus to handle CRUD operations for projects and project-user mappings.
 *
 * @author Shawn Cui
 */
@Slf4j
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectUserMapper projectUserMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * Adds a new project to the system.
     * The project ID is auto-generated, and the creation timestamp and creator ID are populated.
     *
     * @param project The project details to be added.
     * @return The number of rows affected in the database.
     */
    @Override
    public int addProject(Project project) {
        project.setProjectId(IdWorker.getId()) // Generate a unique ID for the project
                .setCreateTime(new Date())     // Set the current timestamp
                .setCreateBy(tokenService.getLoginUser().getUserId()); // Set the creator's user ID
        return projectMapper.insert(project);
    }

    /**
     * Associates the currently logged-in user with the specified project.
     *
     * @param projectId The ID of the project the user wants to join.
     * @return The number of rows affected in the database.
     */
    @Override
    public int joinProject(Long projectId) {
        return projectUserMapper.insert(
                new ProjectUser(projectId, tokenService.getLoginUser().getUserId())
        );
    }

    /**
     * Deletes a project and removes its associations with users.
     *
     * @param projectId The ID of the project to be deleted.
     * @return The number of rows affected in the database.
     */
    @Override
    public int deleteProject(Long projectId) {
        return projectUserMapper.delete(
                new LambdaQueryWrapper<ProjectUser>().eq(ProjectUser::getProjectId, projectId)
        );
    }

    /**
     * Retrieves a list of users associated with a specific project.
     *
     * @param projectId The ID of the project.
     * @return A list of users linked to the specified project.
     */
    @Override
    public List<ProjectUser> getProjectUsers(Long projectId) {
        return projectUserMapper.selectList(
                new LambdaQueryWrapper<ProjectUser>().eq(ProjectUser::getProjectId, projectId)
        );
    }

    /**
     * Retrieves a list of projects a specific user is associated with.
     *
     * @param userId The ID of the user.
     * @return A list of projects linked to the specified user.
     */
    @Override
    public List<ProjectUser> getUserProjects(Long userId) {
        return projectUserMapper.selectList(
                new LambdaQueryWrapper<ProjectUser>().eq(ProjectUser::getUserId, userId)
        );
    }
}