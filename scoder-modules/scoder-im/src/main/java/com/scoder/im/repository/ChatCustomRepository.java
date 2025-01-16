package com.scoder.im.repository;

import com.scoder.im.domain.ChatMessage;
import com.scoder.im.domain.vos.ChatMessageVo;
import com.scoder.im.domain.vos.GroupVo;

import java.util.List;

public interface ChatCustomRepository {
    List<ChatMessageVo> getDirectChatList(Long userId);

    List<GroupVo> getTeamChatList(Long userId);

    List<ChatMessage> getDirectChatHistory(Long userId, Long receiverId);
}