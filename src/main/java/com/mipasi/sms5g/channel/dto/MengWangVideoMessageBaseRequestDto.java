package com.mipasi.sms5g.channel.dto;

import lombok.Data;

/**
 * @author yangg
 * @since 2021/7/19
 */
@Data
public class MengWangVideoMessageBaseRequestDto {

    /**
     * 帐号
     */
    protected String userid;
    /**
     * 密码
     */
    protected String pwd;
    /**
     * 请求时间
     */
    protected String timestamp;


}
