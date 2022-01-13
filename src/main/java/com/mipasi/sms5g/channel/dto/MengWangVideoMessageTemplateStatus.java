package com.mipasi.sms5g.channel.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * ..
 * 梦网模板状态
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
@Slf4j
public class MengWangVideoMessageTemplateStatus {
    /**
     * 通道模板id
     */
    private String tplid;
    /**
     * 平台模板编号
     */
    private String custid;
    /**
     * 模板大小，单位字节
     */
    private Long tplsize;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 档位
     */
    private int chgrade;
    /**
     * 有效期，0标识永久有效
     */
    private Long validtime;
    /**
     * 模板状态
     */
    private int status;
    /**
     * 状态描述
     */
    private String desc;
    /**
     * 运营商模板状态详情
     */
    private List<MengWangVideoMessageTemplateStatusDetail> statusdetail;
    /**
     * 预览地址
     */
    private String previewurl;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 签名
     */
    private String tplsign;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 动参信息
     */
    private List<MengWangTemplateVarInfo> varinfo;
}
