package com.mipasi.sms5g.channel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * ..
 * 视频短信模板审核状态查询
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MengWangVideoMessageTemplateStatusResponse extends MengWangVideoMessageBaseResponse {
    /**
     * 状态列表
     */
    private List<MengWangVideoMessageTemplateStatus> tplsts;

}
