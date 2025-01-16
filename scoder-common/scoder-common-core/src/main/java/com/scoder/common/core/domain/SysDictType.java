package com.scoder.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Represents a dictionary type entity.
 * Used for managing dictionary types in the system.
 *
 * @author Shawn Cui
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_dict_type")
public class SysDictType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary key of the dictionary.
     */
    private Long dictId;

    /**
     * Name of the dictionary.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name length cannot exceed 100 characters")
    private String dictName;

    /**
     * Type of the dictionary.
     */
    @NotBlank(message = "Type cannot be blank")
    @Size(max = 100, message = "Type length cannot exceed 100 characters")
    private String dictType;

    /**
     * Status of the dictionary ('0' for active, '1' for inactive).
     */
    private String status;
}