package com.mipasi.sms5g.channel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ..
 * 梦网消息发送请求
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MengWangVideoMessageSendRequestDto extends MengWangVideoMessageBaseRequestDto {

    /**
     * 模板id
     */
    private String tplid;
    /**
     * 消息有效期
     */
    private String expire;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 动参键值对
     */
    private String content;
    /**
     * 业务类型
     */
    private String svrtype;
    /**
     * 扩展号
     */
    private String exno;
    /**
     * 平台消息id
     */
    private String custid;
    /**
     * 自定义扩展数据
     */
    private String exdata;


}
