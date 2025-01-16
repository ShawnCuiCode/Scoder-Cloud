package com.scoder.im.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoder.im.api.domain.Group;
import com.scoder.im.config.NettyConfig;
import com.scoder.im.domain.ChatMessage;
import com.scoder.im.repository.ChatRepository;
import com.scoder.im.repository.GroupRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Handles WebSocket events and processes messages sent by clients.
 * Supports user login, direct messaging, and group messaging.
 * Messages are persisted in the database and delivered to online users in real time.
 *
 * @author Shawn Cui
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    @Autowired
    private GroupRepository groupRepository; // Repository for managing group data
    @Autowired
    private ChatRepository chatRepository; // Repository for managing chat messages

    /**
     * Called when a new connection is established.
     *
     * @param ctx The channel context
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("New connection added: {}", ctx.channel().id().asLongText());
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * Processes incoming messages from clients.
     *
     * @param ctx The channel context
     * @param msg The received WebSocket text frame
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String messageText = msg.text();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(messageText);

            String type = jsonNode.get("type").asText();

            if ("LOGIN".equals(type)) {
                // Handle user login
                String userId = jsonNode.get("userId").asText();
                bindUserToChannel(ctx, userId);
            } else if ("DIRECT".equals(type)) {
                // Handle direct message
                ChatMessage chatMessage = objectMapper.treeToValue(jsonNode, ChatMessage.class);
                handleDirectMessage(chatMessage);
            } else if ("GROUP".equals(type)) {
                // Handle group message
                ChatMessage chatMessage = objectMapper.treeToValue(jsonNode, ChatMessage.class);
                handleGroupMessage(chatMessage);
            }
        } catch (Exception e) {
            log.error("Failed to process message: {}", e.getMessage());
        }
    }

    /**
     * Handles direct messages and sends them to the recipient if they are online.
     *
     * @param chatMessage The chat message
     */
    private void handleDirectMessage(ChatMessage chatMessage) {
        String receiverId = chatMessage.getReceiverId().toString();
        Channel receiverChannel = NettyConfig.getUserChannelMap().get(receiverId);
        HashMap<String, String> map = new HashMap<>();
        map.put("receiverId", receiverId);
        map.put("senderId", chatMessage.getSenderId().toString());
        map.put("content", chatMessage.getContent());
        map.put("timestamp", chatMessage.getTimestamp().toString());
        map.put("type", chatMessage.getType());
        chatMessage.setTimestamp(System.currentTimeMillis());
        if (receiverChannel != null && receiverChannel.isActive()) {
            receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
        }
        chatRepository.save(chatMessage);
        log.info("Direct message sent to user {}: {}", receiverId, JSON.toJSONString(chatMessage));

    }

    /**
     * Handles group messages and sends them to all online members.
     *
     * @param chatMessage The chat message
     */
    private void handleGroupMessage(ChatMessage chatMessage) {
        Long teamId = chatMessage.getTeamId();
        Group group = groupRepository.findByTeamId(teamId);
        chatMessage.setTimestamp(System.currentTimeMillis());
        for (Long memberId : group.getMemberIds()) {
            String memberIdStr = memberId.toString();
            Channel memberChannel = NettyConfig.getUserChannelMap().get(memberIdStr);
            HashMap<String, String> map = new HashMap<>();
            map.put("senderId", chatMessage.getSenderId().toString());
            map.put("content", chatMessage.getContent());
            map.put("timestamp", chatMessage.getTimestamp().toString());
            map.put("type", chatMessage.getType());
            if (memberChannel != null && memberChannel.isActive()) {
                memberChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
            }
            log.info("Group message sent to member {}: {}", memberId, JSON.toJSONString(chatMessage));
            chatRepository.save(chatMessage);
        }
    }

    /**
     * Called when a connection is removed.
     *
     * @param ctx The channel context
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        String userId = getUserIdFromChannel(ctx.channel());
        if (userId != null) {
            NettyConfig.getUserChannelMap().remove(userId);
            log.info("User {} has been unbound from channel: {}", userId, ctx.channel().id());
        }
        NettyConfig.getChannelGroup().remove(ctx.channel());
    }

    /**
     * Called when an exception occurs during processing.
     *
     * @param ctx   The channel context
     * @param cause The exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String userId = getUserIdFromChannel(ctx.channel());
        log.error("Exception for user {}: {}", userId, cause.getMessage());

        removeChannel(ctx);
        if (ctx.channel().isActive()) {
            ctx.close();
        }
    }

    /**
     * Binds a user ID to a channel.
     *
     * @param ctx    The channel context
     * @param userId The user ID
     */
    private void bindUserToChannel(ChannelHandlerContext ctx, String userId) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        ctx.channel().attr(key).set(userId);
        NettyConfig.getUserChannelMap().put(userId, ctx.channel());
        log.info("User {} is now bound to channel: {}", userId, ctx.channel().id());
    }

    /**
     * Retrieves the user ID associated with a channel.
     *
     * @param channel The channel object
     * @return The user ID
     */
    private String getUserIdFromChannel(Channel channel) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        return channel.attr(key).get();
    }

    /**
     * Removes a user-channel binding and cleans up the channel group.
     *
     * @param ctx The channel context
     */
    private void removeChannel(ChannelHandlerContext ctx) {
        String userId = getUserIdFromChannel(ctx.channel());
        if (userId != null) {
            NettyConfig.getUserChannelMap().remove(userId);
        }
        NettyConfig.getChannelGroup().remove(ctx.channel());
    }
}