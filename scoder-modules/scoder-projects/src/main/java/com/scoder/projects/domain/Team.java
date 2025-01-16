package com.scoder.projects.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.scoder.common.core.handler.JsonListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents a team entity in the system.
 * Includes team details like skills, goals, and members.
 * Maps to the `t_team` table in the database.
 *
 * @author Shawn Cui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    // Unique identifier for the team
    @TableId(value = "team_id", type = IdType.INPUT)
    private Long teamId;

    // Identifier for the related project
    @TableField("project_id")
    private Long projectId;

    // URL or path for the team's avatar image
    @TableField("avatar")
    private String avatar;

    // Name of the team
    @TableField("team")
    private String team;

    // Description of the team's purpose or mission
    @TableField("description")
    private String description;

    // List of skill IDs associated with the team
    @TableField(value = "skills", typeHandler = JsonListTypeHandler.class)
    private List<Long> skills;

    // Total experience level of the team
    @TableField("experience")
    private Long experience;

    // Team's goals or objectives
    @TableField("goal")
    private Long goal;

    // Number of members in the team
    @TableField("team_number")
    private Integer teamNumber;

    // Timestamp for when the team was created
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // ID of the user who created the team
    @TableField("create_by")
    private Long createBy;
}