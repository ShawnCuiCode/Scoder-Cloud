package com.scoder.projects.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Represents the association between a team, a project, and a user.
 * This class is mapped to the "t_team_user" database table.
 *
 * @author Shawn Cui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_team_user") // Maps the class to the "t_team_user" table in the database.
public class TeamUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The ID of the associated project.
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * The ID of the associated team.
     */
    @TableField("team_id")
    private Long teamId;

    /**
     * The ID of the associated user.
     */
    @TableField("user_id")
    private Long userId;

}