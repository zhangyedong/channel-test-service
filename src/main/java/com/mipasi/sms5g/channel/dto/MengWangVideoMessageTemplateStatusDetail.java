package com.mipasi.sms5g.channel.dto;

import lombok.Data;

/**
 * ..
 * 梦网消息模板状态详情对象
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
public class MengWangVideoMessageTemplateStatusDetail {

    /**
     * 运营商
     */
    private String carrier;
    /**
     * 状态
     */
    private int status;
    /**
     * 状态描述
     */
    private String desc;
}
