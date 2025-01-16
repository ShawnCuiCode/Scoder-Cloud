package com.scoder.im.repository;

import com.scoder.common.core.utils.bean.BeanUtils;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.im.api.domain.Group;
import com.scoder.im.domain.ChatMessage;
import com.scoder.im.domain.vos.ChatMessageVo;
import com.scoder.im.domain.vos.GroupVo;
import com.scoder.user.api.RemoteUserService;
import com.scoder.user.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class ChatCustomRepositoryImpl implements ChatCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public List<ChatMessageVo> getDirectChatList(Long userId) {
        List<AggregationOperation> operations = new ArrayList<>();

        Criteria matchCriteria = new Criteria();

        matchCriteria.orOperator(
                        Criteria.where("senderId").is(userId),
                        Criteria.where("receiverId").is(userId)
                )
                .and("type").is("DIRECT");

        operations.add(match(matchCriteria));

        AggregationOperation sort = sort(Sort.by(Sort.Direction.DESC, "timestamp"));
        operations.add(sort);

        AggregationOperation limit = limit(1);
        operations.add(limit);

        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<ChatMessage> results = mongoTemplate.aggregate(aggregation, "messages", ChatMessage.class);

        List<ChatMessageVo> chatMessageVos = new ArrayList<>();
        results.getMappedResults().forEach(chatMessage -> {
            ChatMessageVo chatMessageVo = new ChatMessageVo();
            BeanUtils.copyProperties(chatMessage, chatMessageVo);
            Long otherUserId = chatMessage.getSenderId().equals(userId) ?
                    chatMessage.getReceiverId() : chatMessage.getSenderId();
            AjaxResult<SysUser> userResult = remoteUserService.getUserById(otherUserId);
            if (userResult != null && userResult.getData() != null) {
                chatMessageVo.setNickName(userResult.getData().getNickName());
                chatMessageVo.setAvatar(userResult.getData().getAvatar());
                chatMessageVo.setSenderId(userId);
                chatMessageVo.setReceiverId(otherUserId);
            }
            chatMessageVos.add(chatMessageVo);
        });

        return chatMessageVos;
    }

    @Override
    public List<GroupVo> getTeamChatList(Long userId) {
        // Find all groups where the user is a member
        List<Group> userGroups = mongoTemplate.find(
                new Query(Criteria.where("memberIds").is(userId)), Group.class, "groups");

        if (userGroups.isEmpty()) {
            return List.of(); // Return an empty list if the user is not part of any groups
        }

        // Extract group IDs from the user's group list
        List<String> groupIds = userGroups.stream()
                .map(Group::getId).collect(Collectors.toList());

        // Retrieve the last message in each group, sorted by timestamp in descending order
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(match(Criteria.where("groupId").in(groupIds))); // Match messages in the user's groups
        aggregationOperations.add(sort(Sort.by(Sort.Direction.DESC, "timestamp"))); // Sort messages by timestamp (descending)
        aggregationOperations.add(group("groupId") // Group messages by group ID
                .first("content").as("lastMessageContent") // Select the content of the latest message
                .first("timestamp").as("lastMessageTimestamp")); // Select the timestamp of the latest message

        // Perform the aggregation query
        Aggregation aggregation = newAggregation(aggregationOperations);
        AggregationResults<ChatMessage> results = mongoTemplate.aggregate(
                aggregation, "messages", ChatMessage.class);

        List<ChatMessage> lastMessages = results.getMappedResults();

        // Map the results and merge group information with the last message
        Map<Long, ChatMessage> groupIdToLastMessageMap = lastMessages.stream()
                .collect(Collectors.toMap(ChatMessage::getTeamId, message -> message));

        return userGroups.stream()
                .map(group -> {
                    ChatMessage lastMessage = groupIdToLastMessageMap.get(group.getTeamId());
                    GroupVo groupVo = new GroupVo();
                    BeanUtils.copyProperties(group, groupVo);
                    if (lastMessage != null) {
                        groupVo.setContent(lastMessage.getContent());
                        groupVo.setTime(lastMessage.getTimestamp());
                    }
                    return groupVo;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ChatMessage> getDirectChatHistory(Long userId, Long receiverId) {
        // Create a new query object
        Query query = new Query();

        // Match messages where the sender and receiver match the given userId and receiverId in either direction
        query.addCriteria(new Criteria().orOperator(
                new Criteria().andOperator(
                        Criteria.where("senderId").is(userId),
                        Criteria.where("receiverId").is(receiverId)
                ),
                new Criteria().andOperator(
                        Criteria.where("senderId").is(receiverId),
                        Criteria.where("receiverId").is(userId)
                )
        ));

        // Sort the results by the timestamp field in ascending order (oldest to newest)
        query.with(Sort.by(Sort.Direction.ASC, "timestamp"));

        // Execute the query and retrieve the matching messages from the "messages" collection
        return mongoTemplate.find(query, ChatMessage.class, "messages");
    }

}