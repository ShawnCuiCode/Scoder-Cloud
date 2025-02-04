package com.socder.projects.controller;

import com.scoder.projects.controller.ProjectController;
import com.scoder.projects.domain.Project;
import com.scoder.projects.service.ProjectService;
import com.scoder.common.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    /**
     * Test the addProject method.
     */
    @Test
    void testAddProject() throws Exception {
        Project project = new Project();
        project.setProjectName("New Project");

        when(projectService.addProject(any(Project.class))).thenReturn(1);

        mockMvc.perform(post("/addProject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"projectName\":\"New Project\"}"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).addProject(any(Project.class));
    }

    /**
     * Test the getProjectList method.
     */
    @Test
    void testGetProjectList() throws Exception {
        when(projectService.list()).thenReturn(Collections.singletonList(new Project()));

        mockMvc.perform(get("/getProjectList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))  // Check for success code in the response
                .andExpect(jsonPath("$.data.length()").value(1)); // Check if the list has data

        verify(projectService, times(1)).list();
    }

    /**
     * Test the getProjectById method.
     */
    @Test
    void testGetProjectById() throws Exception {
        Long projectId = 1L;
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName("Test Project");

        when(projectService.getById(projectId)).thenReturn(project);

        mockMvc.perform(get("/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))  // Check for success code in the response
                .andExpect(jsonPath("$.data.projectName").value("Test Project"));  // Check projectName

        verify(projectService, times(1)).getById(projectId);
    }

    /**
     * Test the updateProject method.
     */
    @Test
    void testUpdateProject() throws Exception {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Updated Project");

        when(projectService.updateById(any(Project.class))).thenReturn(true);

        mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"projectId\":1, \"projectName\":\"Updated Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));  // Check for success code in the response

        verify(projectService, times(1)).updateById(any(Project.class));
    }

    /**
     * Test the deleteProjectById method.
     */
    @Test
    void testDeleteProjectById() throws Exception {
        Long projectId = 1L;

        when(projectService.removeById(projectId)).thenReturn(true);

        mockMvc.perform(delete("/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));  // Check for success code in the response

        verify(projectService, times(1)).removeById(projectId);
    }

    /**
     * Test the joinProject method.
     */
    @Test
    void testJoinProject() throws Exception {
        Long projectId = 1L;

        when(projectService.joinProject(projectId)).thenReturn(1);

        mockMvc.perform(post("/joinProject/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));  // Check for success code in the response

        verify(projectService, times(1)).joinProject(projectId);
    }

    /**
     * Test the getProjectUsers method.
     */
    @Test
    void testGetProjectUsers() throws Exception {
        Long projectId = 1L;

        when(projectService.getProjectUsers(projectId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getProjectUsers/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))  // Check for success code in the response
                .andExpect(jsonPath("$.data").isEmpty());  // Check if no users are returned

        verify(projectService, times(1)).getProjectUsers(projectId);
    }

    /**
     * Test the deleteProject method.
     */
    @Test
    void testDeleteProject() throws Exception {
        Long projectId = 1L;

        when(projectService.deleteProject(projectId)).thenReturn(1);

        mockMvc.perform(delete("/deleteProject/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));  // Check for success code in the response

        verify(projectService, times(1)).deleteProject(projectId);
    }
}