package com.mipasi.sms5g.channel.dto;

import lombok.Data;

/**
 * ..
 * 梦网基础返回对象
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
public class MengWangVideoMessageBaseResponse {
    /**
     * 处理结果
     */
    private int result;
    /**
     * 结果描述
     */
    private String desc;

    public boolean isSuccess() {
        return result == 0;
    }

}
