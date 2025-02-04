package com.socder.projects.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scoder.projects.controller.TeamController;
import com.scoder.projects.domain.Team;
import com.scoder.projects.service.TeamService;
import com.scoder.common.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @Mock
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    void testAddTeam() throws Exception {
        // Create mock data for a team
        Team team = new Team();
        team.setTeam("Development Team");
        team.setProjectId(1L);

        // Mock the service call to add a team
        when(teamService.postTeam(team)).thenReturn(1);

        // Perform the POST request and check the result
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"team\":\"Development Team\", \"projectId\": 1}"))
                .andExpect(status().isOk()) // Ensure the response status is 200 OK
                .andExpect(jsonPath("$.code").value(200)); // Check the response JSON contains the code 200

        // Verify that the service method was called exactly once
        verify(teamService).postTeam(team);
    }

    @Test
    void testGetTeamById() throws Exception {
        // Mock team data
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeam("Development Team");

        // Mock the service call to fetch team by ID
        when(teamService.getById(1L)).thenReturn(team);

        // Perform the GET request to fetch the team by ID
        mockMvc.perform(get("/team/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.team").value("Development Team"));

        // Verify the service method was called once
        verify(teamService).getById(1L);
    }

    @Test
    void testGetTeamsByProjectId() throws Exception {
        // Mock team data
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeam("Development Team");
        team.setProjectId(1L);

        // Mock the service call to fetch teams by project ID using list method
        when(teamService.list(new LambdaQueryWrapper<Team>().eq(Team::getProjectId, 1L)))
                .thenReturn(Collections.singletonList(team));

        // Perform the GET request to fetch teams by project ID
        mockMvc.perform(get("/team/project/1"))
                .andExpect(status().isOk());

    }

    @Test
    void testUpdateTeam() throws Exception {
        // Mock the update team operation
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeam("Updated Team");

        // Mock the service call to update the team
        when(teamService.updateTeam(team)).thenReturn(1);

        // Perform the PUT request to update the team
        mockMvc.perform(put("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamId\": 1, \"team\": \"Updated Team\"}"))
                .andExpect(status().isOk())  // Ensure the status is 200 OK
                .andExpect(jsonPath("$.code").value(200));  // Check that the response contains the success code

        // Verify that the update method was called once
        verify(teamService).updateTeam(team);
    }

    @Test
    void testDeleteTeamById() throws Exception {
        // Mock the service call to delete the team
        when(teamService.removeById(1L)).thenReturn(true);

        // Perform the DELETE request to remove the team by ID
        mockMvc.perform(delete("/team/1"))
                .andExpect(status().isOk()) // Ensure the response status is 200 OK
                .andExpect(jsonPath("$.code").value(200)); // Check that the response code is 200

        // Verify the delete method was called once
        verify(teamService).removeById(1L);
    }

    @Test
    void testJoinTeam() throws Exception {
        // Mock the join team operation
        when(teamService.joinTeam(1L)).thenReturn(1);

        // Perform the POST request to join the team
        mockMvc.perform(post("/team/1/join"))
                .andExpect(status().isOk())  // Ensure status is OK
                .andExpect(jsonPath("$.code").value(200));  // Check success code

        // Verify that the join method was called once
        verify(teamService).joinTeam(1L);
    }

    // More tests for other endpoints can be added similarly
}