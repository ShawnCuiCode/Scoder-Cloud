package com.scoder.im.controller;

import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.im.api.domain.Group;
import com.scoder.im.domain.ChatMessage;
import com.scoder.im.domain.vos.ChatMessageVo;
import com.scoder.im.domain.vos.GroupVo;
import com.scoder.im.service.ChatService;
import com.scoder.im.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IMController handles the primary API endpoints for chat and group functionalities.
 *
 * @author Shawn Cui
 */
@RestController
@RequestMapping
public class IMController {

    @Autowired
    private ChatService chatService; // Service for managing chat operations

    @Autowired
    private GroupService groupService; // Service for managing group-related operations


    @GetMapping("/getDirectChatList/{userId}")
    public AjaxResult<List<ChatMessageVo>> getDirectChatList(@PathVariable("userId") Long userId) {
        return AjaxResult.success(chatService.getDirectChatList(userId));
    }

    @GetMapping("/getTeamChatList/{userId}")
    public AjaxResult<List<GroupVo>> getTeamChatList(@PathVariable("userId") Long userId) {
        return AjaxResult.success(groupService.getTeamChatList(userId));
    }

    /**
     * Fetches the chat history for a specific user.
     *
     * @param userId The ID of the user whose chat history is being retrieved.
     * @return A successful response containing a list of chat messages.
     */
    @GetMapping("/getDirectChatHistory/{userId}/{receiverId}")
    public AjaxResult<List<ChatMessage>> getDirectChatHistory(@PathVariable Long userId, @PathVariable("receiverId") Long receiverId) {
        return AjaxResult.success(chatService.getDirectChatHistory(userId, receiverId));
    }

    /**
     * Fetches the chat history for a specific group.
     *
     * @param teamId The ID of the group whose chat history is being retrieved.
     * @return A successful response containing a list of chat messages.
     */
    @GetMapping("/getTeamChatHistory/{teamId}")
    public AjaxResult<List<ChatMessage>> getTeamChatHistory(@PathVariable Long teamId) {
        return AjaxResult.success(chatService.getTeamChatHistory(teamId));
    }

    /**
     * Creates a new group based on the provided details.
     *
     * @param group The group object containing the details for the new group.
     * @return A successful response containing the newly created group.
     */
    @PostMapping("/group/createGroup")
    public AjaxResult<Group> createGroup(@RequestBody Group group) {
        return AjaxResult.success(groupService.createGroup(group));
    }

    @PostMapping("/joinTeam/{teamId}/{userId}")
    public AjaxResult joinTeam(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {
        return AjaxResult.success(groupService.joinTeam(teamId, userId));
    }

    @PutMapping("/updateTeam")
    public AjaxResult updateTeam(@RequestBody Group group) {
        return AjaxResult.success(groupService.updateTeam(group));
    }
}