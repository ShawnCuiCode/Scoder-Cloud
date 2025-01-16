package com.scoder.im.service;

import com.scoder.im.domain.ChatMessage;
import com.scoder.im.domain.vos.ChatMessageVo;
import com.scoder.im.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ChatService handles chat-related operations, including retrieving
 * chat histories for individual users and groups.
 * <p>
 * It serves as the service layer for chat message management,
 * interacting with the database through ChatRepository.
 *
 * @author Shawn Cui
 */
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository; // Repository for managing chat messages

    /**
     * Retrieves the chat history for a specific user. This includes all messages
     * sent by or received by the user.
     *
     * @param userId The ID of the user whose chat history is to be retrieved.
     * @return A list of chat messages involving the specified user.
     */
    public List<ChatMessage> getChatHistory(Long userId) {
        return chatRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    /**
     * Retrieves the chat history for a specific group. This includes all messages
     * sent within the group.
     *
     * @param teamId The ID of the group whose chat history is to be retrieved.
     * @return A list of chat messages sent in the specified group.
     */
    public List<ChatMessage> getTeamChatHistory(Long teamId) {
        return chatRepository.findByTeamId(teamId);
    }

    public List<ChatMessageVo> getDirectChatList(Long userId) {
        return chatRepository.getDirectChatList(userId);
    }

    public List<ChatMessage> getDirectChatHistory(Long userId, Long receiverId) {
        return chatRepository.getDirectChatHistory(userId, receiverId);
    }
}