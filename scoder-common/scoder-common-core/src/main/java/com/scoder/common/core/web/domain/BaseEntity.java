package com.scoder.common.core.web.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Base entity class for common fields.
 * <p>
 * This class provides standard fields for entities such as creation and update metadata, remarks, and additional parameters.
 *
 * @author Shawn Cui
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Search value for filtering or searching operations.
     */
    private String searchValue;

    /**
     * The user who created the entity.
     * Automatically populated during insertion.
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * The timestamp when the entity was created.
     * Automatically populated during insertion.
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * The user who last updated the entity.
     * Automatically populated during insertion and update.
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * The timestamp when the entity was last updated.
     * Automatically populated during insertion and update.
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * Additional remarks or comments about the entity.
     */
    private String remark;

    /**
     * Request parameters for custom or dynamic data handling.
     * This field is not persisted in the database.
     */
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}