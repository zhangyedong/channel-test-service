package com.mipasi.sms5g.channel.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 移动通道视频短信对象
 */
@Data
public class MobileVideoMessageDto implements Serializable {

    /**
     * 一个或多个id
     */
    private JSONObject id;
    /**
     * 内容详情
     */
    private JSONObject content;
    /**
     * 扩展字段
     */
    private JSONObject option;

    /**
     * 签名
     */
    private String sign;
}
