package com.scoder.user.api.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoder.common.core.handler.JsonListTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user
 *
 * @author Shawn Cui
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    @NotBlank(message = "username can not be null")
    @Size(min = 0, max = 30, message = "max 30 char")
    private String userName;

    @Size(min = 0, max = 30, message = "max 30 char")
    private String nickName;

    private String gender;

    private String avatar;

    private String email;

    @ApiModelProperty("0=admin  1=student 2=professor")
    private String userType;

    @JsonProperty
    private String password;

    private String status;

    private String delFlag;

    private String loginIp;

    private Date loginDate;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String remark;

    @TableField(value = "skills", typeHandler = JsonListTypeHandler.class)
    private List<Long> skills;

    private Long goal;

    private Long experience;


}
