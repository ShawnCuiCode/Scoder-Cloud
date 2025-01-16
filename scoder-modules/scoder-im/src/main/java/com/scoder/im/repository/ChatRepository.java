package com.scoder.im.repository;

import com.scoder.im.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<ChatMessage, Long>, ChatCustomRepository {
    List<ChatMessage> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    List<ChatMessage> findByTeamId(Long teamId);

}