package com.scoder.projects.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a project entity in the system.
 * This class is mapped to the "t_project" database table and is used to store project details.
 * It includes information like the project ID, avatar, name, description, and creation details.
 * <p>
 * The class is annotated for use with MyBatis Plus and Jackson.
 *
 * @author Shawn Cui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_project") // Maps the class to the "t_project" table in the database.
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the project.
     * This field is the primary key and is set manually (IdType.INPUT).
     */
    @TableId(value = "project_id", type = IdType.INPUT)
    private Long projectId;

    /**
     * The avatar or image URL associated with the project.
     */
    @TableField("avatar")
    private String avatar;

    /**
     * The name of the project.
     */
    @TableField("project_name")
    private String projectName;

    /**
     * A brief description of the project.
     */
    @TableField("description")
    private String description;

    /**
     * The timestamp indicating when the project was created.
     * This field is formatted as "yyyy-MM-dd HH:mm:ss" when serialized to JSON.
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * The ID of the user who created the project.
     */
    @TableField("create_by")
    private Long createBy;
}