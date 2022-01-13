package com.mipasi.sms5g.channel.service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mipasi.sms5g.channel.dto.MengWangStatusReport;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageSendRequestDto;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageSendResponse;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageStatusReportResponse;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageTemplateStatus;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageTemplateStatusDetail;
import com.mipasi.sms5g.channel.dto.MengWangVideoMessageTemplateStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyd
 * @Description: 梦网服务
 * @date 2022/1/6
 */
@Service
@Slf4j
public class MengWangService {

    @Resource
    private RestTemplate restTemplate;

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 100,
            60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024), new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());


    public String addTemplate(String request) {
        String tplId = RandomUtil.randomNumbers(6);
        String custId = JSON.parseObject(request).getString("custid");
        try {
            JSONObject result = new JSONObject();
            result.put("result", "0");
            result.put("desc", "成功");
            result.put("custid", custId);
            result.put("tplid", tplId);

            THREAD_POOL.execute(() -> {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //tplId
                MengWangVideoMessageTemplateStatusResponse templateStatus = new MengWangVideoMessageTemplateStatusResponse();
                List<MengWangVideoMessageTemplateStatus> templateStatusList = new ArrayList<>(1);
                MengWangVideoMessageTemplateStatus status = new MengWangVideoMessageTemplateStatus();

                List<MengWangVideoMessageTemplateStatusDetail> statusDetails = new ArrayList<>();
                MengWangVideoMessageTemplateStatusDetail cmcc = new MengWangVideoMessageTemplateStatusDetail();
                cmcc.setCarrier("cmcc");
                cmcc.setStatus(0);
                cmcc.setDesc("正常可用");
                MengWangVideoMessageTemplateStatusDetail cucc = new MengWangVideoMessageTemplateStatusDetail();
                cucc.setCarrier("cucc");
                cucc.setStatus(0);
                cucc.setDesc("正常可用");
                MengWangVideoMessageTemplateStatusDetail ctcc = new MengWangVideoMessageTemplateStatusDetail();
                ctcc.setCarrier("ctcc");
                ctcc.setStatus(0);
                ctcc.setDesc("正常可用");
                statusDetails.add(cmcc);
                statusDetails.add(cucc);
                statusDetails.add(ctcc);

                status.setTplid(tplId);
                status.setCustid(custId);
                status.setTplsize(200L);
                status.setTimestamp(System.currentTimeMillis() + "");
                status.setChgrade(1);
                status.setValidtime(0L);
                status.setStatus(0);
                status.setDesc("正常使用");
                status.setStatusdetail(statusDetails);
                status.setPreviewurl("www.baidu.com");
                status.setName("name");
                status.setTitle("title");
                status.setTplsign("梦网科技");
                status.setCreateTime(new Date());
                status.setVarinfo(null);
                templateStatusList.add(status);
                templateStatus.setTplsts(templateStatusList);
                templateStatus.setResult(0);

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/videomessage/mengwang/callback/tmplate/status", templateStatus, String.class);
            });

            return result.toJSONString();
        } catch (Exception e) {
            log.error("添加梦网模板失败", e);
        }
        return null;
    }

    public String sendMessage(String request) {
        log.info("梦网发送消息：request：{}", request);
        MengWangVideoMessageSendRequestDto mengWangVideoMessageSendRequestDto = JSON.parseObject(request, MengWangVideoMessageSendRequestDto.class);
        String phoneList = mengWangVideoMessageSendRequestDto.getMobile();
        String custId = mengWangVideoMessageSendRequestDto.getCustid();
        String taskId = IdUtil.fastSimpleUUID();

        //请求结果
        boolean requestResult = true;

        try {

            THREAD_POOL.execute(() -> {

                //状态报告
                boolean statusReport = true;
                //指定状态报告成功的手机号
                List<String> successPhonesList = Arrays.asList("18375893885", "18581456688");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MengWangVideoMessageStatusReportResponse reportResponse = new MengWangVideoMessageStatusReportResponse();
                List<MengWangStatusReport> statusReports = new ArrayList<>();
                for (String p : phoneList.split(",")) {
                    MengWangStatusReport report = new MengWangStatusReport();
                    report.setMsgid(taskId);
                    report.setCustid(custId);
                    report.setMobile(p);
                    report.setExno("666888");
                    report.setRtime(getCurrentDate());
                    if (statusReport) {
                        if (!successPhonesList.isEmpty() && successPhonesList.contains(p)) {
                            report.setStatus(0);
                        } else {
                            report.setStatus(1);
                        }
                    } else {
                        report.setStatus(1);
                    }
                    statusReports.add(report);
                }
                reportResponse.setRpts(statusReports);

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/videomessage/mengwang/callback/statusreport", reportResponse, String.class);

            });

            MengWangVideoMessageSendResponse sendResponse = new MengWangVideoMessageSendResponse();
            if (requestResult) {
                sendResponse.setMsgid(taskId);
                sendResponse.setCustid(custId);
                sendResponse.setResult(0);
            } else {
                sendResponse.setMsgid("0");
                sendResponse.setResult(1);
                sendResponse.setDesc("请求失败");
            }

            return JSON.toJSONString(sendResponse);

        } catch (Exception e) {
            log.error("发送梦网消息异常", e);
        }
        return null;
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
