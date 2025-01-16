package com.scoder.projects.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scoder.common.core.web.controller.BaseController;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.common.security.service.TokenService;
import com.scoder.projects.domain.Project;
import com.scoder.projects.domain.ProjectUser;
import com.scoder.projects.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles HTTP requests for managing projects and their related data.
 * Provides endpoints for creating, retrieving, updating, and deleting projects,
 * as well as managing user participation in projects.
 *
 * @author Shawn Cui
 */
@Slf4j
@RestController
@RequestMapping
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TokenService tokenService;

    /**
     * Creates a new project.
     *
     * @param project The project details provided in the request body.
     * @return A result indicating whether the operation was successful.
     */
    @PostMapping
    public AjaxResult addProject(@Validated @RequestBody Project project) {
        log.info("add project: " + project);
        return toAjax(projectService.addProject(project));
    }

    /**
     * Retrieves a list of all projects.
     *
     * @return A list of all projects in the database.
     */
    @GetMapping("/getProjectList")
    public AjaxResult<List<Project>> getProjectList() {
        return AjaxResult.success(projectService.list());
    }

    @GetMapping("/getMyProjectList")
    public AjaxResult<List<Project>> getMyProjectList() {
        return AjaxResult.success(projectService.list(new LambdaQueryWrapper<Project>().eq(Project::getCreateBy, tokenService.getLoginUser().getUserId())));
    }

    /**
     * Retrieves details for a specific project by its ID.
     *
     * @param projectId The ID of the project to retrieve.
     * @return The details of the specified project.
     */
    @GetMapping("/{projectId}")
    public AjaxResult<Project> getProjectById(@PathVariable("projectId") Long projectId) {
        return AjaxResult.success(projectService.getById(projectId));
    }

    /**
     * Updates the details of an existing project.
     *
     * @param project The updated project details provided in the request body.
     * @return A result indicating whether the update operation was successful.
     */
    @PutMapping
    public AjaxResult updateProject(@Validated @RequestBody Project project) {
        return toAjax(projectService.updateById(project));
    }

    /**
     * Deletes a specific project by its ID.
     *
     * @param projectId The ID of the project to delete.
     * @return A result indicating whether the delete operation was successful.
     */
    @DeleteMapping("/{projectId}")
    public AjaxResult deleteProjectById(@PathVariable("projectId") Long projectId) {
        return toAjax(projectService.removeById(projectId));
    }

    /**
     * Adds the current user to a specific project.
     *
     * @param projectId The ID of the project to join.
     * @return A result indicating whether the join operation was successful.
     */
    @PostMapping("joinProject/{projectId}")
    public AjaxResult joinProject(@PathVariable("projectId") Long projectId) {
        return toAjax(projectService.joinProject(projectId));
    }

    /**
     * Retrieves all users associated with a specific project.
     *
     * @param projectId The ID of the project to fetch users for.
     * @return A list of users who are part of the specified project.
     */
    @GetMapping("getProjectUsers/{projectId}")
    public AjaxResult<List<ProjectUser>> getProjectUsers(@PathVariable("projectId") Long projectId) {
        return AjaxResult.success(projectService.getProjectUsers(projectId));
    }

    /**
     * Retrieves all projects a specific user is part of.
     *
     * @param userId The ID of the user to fetch projects for.
     * @return A list of projects the user is associated with.
     */
    @GetMapping("getUserProjects/{userId}")
    public AjaxResult<List<ProjectUser>> getUserProjects(@PathVariable("userId") Long userId) {
        return AjaxResult.success(projectService.getUserProjects(userId));
    }

    /**
     * Deletes a project by its ID. This operation may involve additional cleanup
     * such as removing associated users or data.
     *
     * @param projectId The ID of the project to delete.
     * @return A result indicating whether the delete operation was successful.
     */
    @DeleteMapping("deleteProject/{projectId}")
    public AjaxResult deleteProject(@PathVariable("projectId") Long projectId) {
        return toAjax(projectService.deleteProject(projectId));
    }
}