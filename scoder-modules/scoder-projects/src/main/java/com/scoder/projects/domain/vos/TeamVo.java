package com.scoder.projects.domain.vos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.scoder.projects.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents a view object (VO) for team-related data.
 * Extends the Team entity and adds additional fields used for matching and visualization.
 * <p>
 * This class is primarily used for transferring enriched team data between services or to the front end.
 *
 * @author Shawn Cui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_team") // Maps this VO to the "t_team" table for MyBatis Plus.
public class TeamVo extends Team {

    /**
     * The matching score for goals between the user and the team.
     */
    private Double goalsMatch;

    /**
     * The matching score for skills between the user and the team.
     */
    private Double skillMatch;

    /**
     * The matching score for experience between the user and the team.
     */
    private Double experienceMatch;

    /**
     * The overall matching score for the user and the team.
     */
    private Double matchScore;

    /**
     * Indicates whether the user has joined the team.
     * 0 - User is already in the team.
     * 1 - User is not in the team.
     */
    private Integer isJoined;

    /**
     * The URL of the radar chart image that visualizes the team's match scores.
     */
    private String radarImg;
}