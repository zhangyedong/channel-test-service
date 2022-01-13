package com.mipasi.sms5g.channel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * ..
 * 状态报告响应
 *
 * @author yangg
 * @since 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MengWangVideoMessageStatusReportResponse extends MengWangVideoMessageBaseResponse {
    /**
     * 状态报告
     */
    private List<MengWangStatusReport> rpts;

}
