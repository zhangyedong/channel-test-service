package com.mipasi.sms5g.channel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ..
 * 梦网视频短信发送响应
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MengWangVideoMessageSendResponse extends MengWangVideoMessageBaseResponse {
    /**
     * 消息id
     */
    private String msgid;
    /**
     * 自定义流水号
     */
    private String custid;

}
