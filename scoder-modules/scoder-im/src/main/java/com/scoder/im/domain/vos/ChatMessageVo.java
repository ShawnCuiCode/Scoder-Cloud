package com.scoder.im.domain.vos;

import com.scoder.im.domain.ChatMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessageVo extends ChatMessage {
    private String nickName;
    private String avatar;
}
