package com.scoder.im.domain.vos;

import com.scoder.im.api.domain.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupVo extends Group {
    private String content;
    private Long time;
}
