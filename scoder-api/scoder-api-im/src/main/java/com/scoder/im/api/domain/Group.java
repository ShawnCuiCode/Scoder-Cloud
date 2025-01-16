package com.scoder.im.api.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a group entity stored in the "groups" collection.
 * This entity defines the structure of a chat group, including its members and metadata.
 * <p>
 * Fields include the group name, member IDs, creator ID, and a timestamp indicating when the group was created.
 *
 * @author Shawn Cui
 */
@Data
@Document(collection = "groups")
public class Group {

    /**
     * The unique identifier for the group.
     */
    @Id
    private String id;

    private Long teamId;

    /**
     * The name of the group.
     */
    private String name;

    private String avatar;

    private String description;

    /**
     * A list of IDs representing the members of the group.
     */
    private List<Long> memberIds;

    /**
     * The ID of the user who created the group.
     */
    private Long createBy;

    /**
     * The timestamp when the group was created, in milliseconds since epoch.
     */
    private Long timestamp;
}