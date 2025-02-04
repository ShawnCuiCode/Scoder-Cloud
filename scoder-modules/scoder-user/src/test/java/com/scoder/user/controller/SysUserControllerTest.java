package com.scoder.user.controller;

import com.scoder.user.api.model.LoginUser;
import com.scoder.user.service.ISysUserService;
import com.scoder.user.api.domain.SysUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SysUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ISysUserService userService;

    @InjectMocks
    private SysUserController sysUserController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sysUserController).build();
    }

    @Test
    void testGetUserList() throws Exception {
        // Mock the service response
        when(userService.list()).thenReturn(List.of(new SysUser(), new SysUser()));

        mockMvc.perform(get("/getUserList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200)) // assuming success response code
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(userService, times(1)).list();
    }

    @Test
    void testGetUserByUserName() throws Exception {
        // Prepare the test data
        String username = "testUser";
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        when(userService.selectUserByUserName(username)).thenReturn(sysUser);

        mockMvc.perform(get("/getUserByUserName/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userName").value(username));

        verify(userService, times(1)).selectUserByUserName(username);
    }

    @Test
    void testRegister() throws Exception {
        // Create a complete SysUser object with necessary fields
        SysUser user = new SysUser();
        user.setUserName("newUser");
        user.setNickName("New User");
        user.setEmail("user@example.com");
        user.setGender("M");
        user.setAvatar("http://example.com/avatar.jpg");
        user.setUserType("1");
        user.setPassword("password123");
        user.setStatus("0");
        user.setDelFlag("0");
        user.setLoginIp("192.168.1.1");
        user.setLoginDate(new Date());
        user.setRemark("New user registration");

        // Mock the checks for username and email uniqueness
        when(userService.checkUserNameUnique(user.getUserName())).thenReturn("0"); // "0" means unique
        when(userService.checkEmailUnique(user)).thenReturn("0"); // "0" means unique

        // Perform the register API call and check the response
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"newUser\",\"nickName\":\"New User\",\"email\":\"user@example.com\","
                                + "\"gender\":\"M\",\"avatar\":\"http://example.com/avatar.jpg\",\"userType\":\"1\","
                                + "\"password\":\"password123\",\"status\":\"ACTIVE\",\"delFlag\":\"0\",\"loginIp\":\"192.168.1.1\","
                                + "\"remark\":\"New user registration\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).insertUser(any(SysUser.class));
    }
}