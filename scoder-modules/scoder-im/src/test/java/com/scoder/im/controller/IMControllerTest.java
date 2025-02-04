package com.scoder.im.controller;

import com.scoder.im.api.domain.Group;
import com.scoder.im.domain.ChatMessage;
import com.scoder.im.domain.vos.ChatMessageVo;
import com.scoder.im.domain.vos.GroupVo;
import com.scoder.im.service.ChatService;
import com.scoder.im.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IMControllerTest {

    @InjectMocks
    private IMController imController;

    @Mock
    private ChatService chatService;

    @Mock
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imController).build();
    }

    /**
     * Test for getting direct chat list of a user.
     */
    @Test
    void testGetDirectChatList() throws Exception {
        // Mock the chat service response
        ChatMessageVo chatMessageVo = new ChatMessageVo();
        chatMessageVo.setNickName("John Doe");
        chatMessageVo.setAvatar("avatar_url");
        chatMessageVo.setContent("Hello, how are you?");
        when(chatService.getDirectChatList(1L)).thenReturn(Collections.singletonList(chatMessageVo));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/getDirectChatList/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].content").value("Hello, how are you?"))
                .andExpect(jsonPath("$.data[0].nickName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].avatar").value("avatar_url"));

        // Verify that the service method was called once
        verify(chatService, times(1)).getDirectChatList(1L);
    }

    /**
     * Test for getting team chat list of a user.
     */
    @Test
    void testGetTeamChatList() throws Exception {
        // Mock the group service response
        GroupVo groupVo = new GroupVo();
        groupVo.setName("Development Team");
        groupVo.setContent("Team chat content");
        when(groupService.getTeamChatList(1L)).thenReturn(Collections.singletonList(groupVo));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/getTeamChatList/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Development Team"))
                .andExpect(jsonPath("$.data[0].content").value("Team chat content"));

        // Verify the service method was called once
        verify(groupService, times(1)).getTeamChatList(1L);
    }

    /**
     * Test for getting direct chat history between two users.
     */
    @Test
    void testGetDirectChatHistory() throws Exception {
        // Mock the chat service response
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent("How are you?");
        when(chatService.getDirectChatHistory(1L, 2L)).thenReturn(Collections.singletonList(chatMessage));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/getDirectChatHistory/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].content").value("How are you?"));

        // Verify the service method was called once
        verify(chatService, times(1)).getDirectChatHistory(1L, 2L);
    }

    /**
     * Test for creating a new group.
     */
    @Test
    void testCreateGroup() throws Exception {
        // Mock the group service response
        Group group = new Group();
        group.setTeamId(1L);
        group.setName("Development Team");
        group.setDescription("A team for software development");

        when(groupService.createGroup(any())).thenReturn(group);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/group/createGroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Development Team\", \"description\": \"A team for software development\", \"teamId\": 1, \"createBy\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Development Team"))
                .andExpect(jsonPath("$.data.description").value("A team for software development"));

        // Verify the service method was called once
        verify(groupService, times(1)).createGroup(any());
    }

    /**
     * Test for joining a team.
     */
    @Test
    void testJoinTeam() throws Exception {
        // Create a mock Group object
        Group group = new Group();
        group.setId("1");
        group.setName("Test Group");
        group.setTeamId(1L);
        group.setMemberIds(List.of(1L, 2L));
        group.setCreateBy(1L);
        // Mock the group service response to return an AjaxResult with success message
        when(groupService.joinTeam(1L, 2L)).thenReturn(group);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/joinTeam/1/2"))
                .andExpect(status().isOk())  // Expecting HTTP status 200 OK
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("success"));

        // Verify that the groupService.joinTeam method was called once with the correct parameters
        verify(groupService, times(1)).joinTeam(1L, 2L);
    }

    /**
     * Test for updating a team.
     */
    @Test
    void testUpdateTeam() throws Exception {
        // Create a sample Group object to mock
        Group group = new Group();
        group.setTeamId(1L);
        group.setName("Development Team");
        group.setAvatar("team-avatar-url");
        group.setDescription("Team for development");

        when(groupService.updateTeam(any(Group.class))).thenReturn("success");

        // Perform the PUT request to update the team
        mockMvc.perform(put("/updateTeam")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamId\":1, \"name\":\"Development Team\", \"avatar\":\"team-avatar-url\", \"description\":\"Team for development\"}"))
                .andExpect(status().isOk())  // Check for 200 OK status
                .andExpect(jsonPath("$.code").value(200))  // Check for success code
                .andExpect(jsonPath("$.msg").value("success"));  // Check if the response contains "success"

        // Verify that the updateTeam method was called once with the expected arguments
        verify(groupService, times(1)).updateTeam(any(Group.class));
    }
}