package com.scoder.im.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a chat message entity stored in the "messages" collection.
 * This entity supports both direct and group messages.
 * <p>
 * Fields include sender and receiver information, message content, and timestamps.
 * The `type` field indicates whether the message is a direct or group message.
 *
 * @author Shawn Cui
 */
@Data
@Document(collection = "messages")
public class ChatMessage {

    /**
     * The unique identifier for the message.
     */
    @Id
    private String id;

    /**
     * The type of message, e.g., "DIRECT" for direct messages or "GROUP" for group messages.
     */
    private String type;

    /**
     * The ID of the user who sent the message.
     */
    private Long senderId;

    /**
     * The ID of the user receiving the message (null for group messages).
     */
    private Long receiverId;

    /**
     * The ID of the group where the message was sent (null for direct messages).
     */
    private Long teamId;

    /**
     * The actual content of the message.
     */
    private String content;

    /**
     * The timestamp when the message was created, in milliseconds since epoch.
     */
    private Long timestamp;
}