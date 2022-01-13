package com.mipasi.sms5g.channel.dto;

import lombok.Data;

/**
 * ..
 * 梦网状态报告
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
public class MengWangStatusReport {
    /**
     * 消息流水号
     */
    private String msgid;
    /**
     * 平台消息流水号
     */
    private String custid;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 当前条数
     */
    private int pknum;
    /**
     * 总条数
     */
    private int pktotal;
    /**
     * 通道号
     */
    private String spno;
    /**
     * 扩展号
     */
    private String exno;
    /**
     * 发送时间 YYYY-MM-DD HH:MM:SS
     */
    private String stime;
    /**
     * 状态报告返回时间
     */
    private String rtime;
    /**
     * 信息类型
     */
    private String type;
    /**
     * 消息接收状态,0成功 其他失败
     */
    private int status;
    /**
     * 错误代码
     */
    private String errcode;
    /**
     * 错误代码描述
     */
    private String errdesc;
    /**
     * 自定义扩展数据
     */
    private String exdata;

}
