package com.scoder.common.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.scoder.common.core.constant.UserConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Represents dictionary data entity.
 * Used for managing dictionary entries in the system.
 *
 * @author Shawn Cui
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_dict_data")
public class SysDictData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for dictionary data.
     */
    @TableId(value = "dict_code", type = IdType.AUTO)
    private Long dictCode;

    /**
     * Sorting order for dictionary data.
     */
    private Long dictSort;

    /**
     * Label for the dictionary entry.
     */
    @NotBlank(message = "Label cannot be blank")
    @Size(min = 0, max = 100, message = "Label length cannot exceed 100 characters")
    private String dictLabel;

    /**
     * Value associated with the dictionary entry.
     */
    @NotBlank(message = "Value cannot be blank")
    @Size(min = 0, max = 100, message = "Value length cannot exceed 100 characters")
    private String dictValue;

    /**
     * Type of the dictionary entry.
     */
    @NotBlank(message = "Type cannot be blank")
    @Size(min = 0, max = 100, message = "Type length cannot exceed 100 characters")
    private String dictType;

    /**
     * CSS class for additional styling.
     */
    @Size(min = 0, max = 100, message = "CSS class length cannot exceed 100 characters")
    private String cssClass;

    /**
     * Table styling class for dictionary data.
     */
    private String listClass;

    /**
     * Indicates if this is the default entry ('Y' for yes, 'N' for no).
     */
    private String isDefault;

    /**
     * Status of the dictionary entry ('0' for active, '1' for inactive).
     */
    private String status;

    /**
     * Determines if the entry is set as default.
     *
     * @return true if default, false otherwise.
     */
    public boolean getDefault() {
        return UserConstants.YES.equals(this.isDefault);
    }
}