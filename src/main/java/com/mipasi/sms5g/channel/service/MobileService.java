package com.mipasi.sms5g.channel.service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mipasi.sms5g.channel.dto.MobileVideoMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyd
 * @Description: 移动服务
 * @date 2022/1/6
 */
@Service
@Slf4j
public class MobileService {

    @Resource
    private RestTemplate restTemplate;

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 100,
            60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024), new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());

    public String addTemplate(String request) {
        String tplId = RandomUtil.randomNumbers(8);
        try {
            THREAD_POOL.execute(() -> {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MobileVideoMessageDto auditReport = new MobileVideoMessageDto();
                JSONObject id = new JSONObject();
                JSONObject content = new JSONObject();
                JSONObject option = new JSONObject();
                id.put("mmsId", tplId);
                content.put("status", "33003002");
                option.put("resptime", System.currentTimeMillis() + "");
                auditReport.setId(id);
                auditReport.setContent(content);
                auditReport.setOption(option);
                auditReport.setSign("");

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/callback/videomessage/mobile/auditreport", auditReport, String.class);

            });

            MobileVideoMessageDto messageDto = new MobileVideoMessageDto();
            JSONObject id = new JSONObject();
            JSONObject content = new JSONObject();
            JSONObject option = new JSONObject();
            id.put("mmsId", tplId);
            content.put("code", "0");
            content.put("message", "成功");
            option.put("resptime", System.currentTimeMillis() + "");
            messageDto.setId(id);
            messageDto.setContent(content);
            messageDto.setOption(option);
            messageDto.setSign("");

            return JSON.toJSONString(messageDto);
        } catch (Exception e) {
            log.error("移动添加模板失败");
        }

        return null;
    }

    public String uploadResource(String request) {
        log.info("移动上传资源：request：{}", request);
        MobileVideoMessageDto messageDto = JSON.parseObject(request, MobileVideoMessageDto.class);

        String tplId = messageDto.getId().getString("mmsId");
        String productId = messageDto.getId().getString("productId");
        String resourceId = RandomUtil.randomNumbers(3);
        boolean uploadResult = true;
        try {

            THREAD_POOL.execute(() -> {

                boolean resourceReportSuccess = true;

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MobileVideoMessageDto resourceReport = new MobileVideoMessageDto();
                JSONObject id = new JSONObject();
                JSONObject content = new JSONObject();
                JSONObject option = new JSONObject();
                id.put("resourceGroupId", resourceId);
                if (resourceReportSuccess) {
                    content.put("status", "31005006");
                    content.put("rejectReason", "");
                } else {
                    content.put("status", "31005007");
                    content.put("rejectReason", "资源文件审核失败");
                }
                option.put("resptime", System.currentTimeMillis() + "");
                resourceReport.setId(id);
                resourceReport.setContent(content);
                resourceReport.setOption(option);
                resourceReport.setSign("");

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/callback/videomessage/mobile/resource/auditreport", resourceReport, String.class);

            });

            MobileVideoMessageDto result = new MobileVideoMessageDto();
            JSONObject id = new JSONObject();
            JSONObject content = new JSONObject();
            JSONObject option = new JSONObject();
            id.put("mmsId", tplId);
            id.put("productId", productId);
            if (uploadResult) {
                id.put("resourceGroupId", resourceId);
                content.put("code", "0");
                content.put("message", "成功");
                content.put("result", "");
            } else {
                content.put("code", "1");
                content.put("message", "失败");
                content.put("result", "请求失败");
            }
            option.put("resptime", System.currentTimeMillis() + "");
            result.setId(id);
            result.setContent(content);
            result.setOption(option);
            result.setSign("");

            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.error("移动上传资源异常", e);
        }

        return null;
    }

    public String sendMessage(String request, Boolean staticTemplate) {
        log.info("移动发送消息：request：{}", request);
        MobileVideoMessageDto messageDto = JSON.parseObject(request, MobileVideoMessageDto.class);
        String taskId = RandomUtil.randomNumbers(5);

        String productId = messageDto.getId().getString("productId");
        String tplId = messageDto.getId().getString("mmsId");
        String taskIdSerial = IdUtil.fastSimpleUUID();
        List<String> phoneList = new ArrayList<>();
        try {

            //静态模板下十六进制解密，然后读取字节流取号码
            if (staticTemplate) {
                String mobileFile = messageDto.getOption().getString("mobileFile");
                byte[] bytes = HexUtil.decodeHex(mobileFile);
                Path tempFile = null;
                try {
                    tempFile = Files.createTempFile("video_message_phone", ".txt");
                    Path path = Files.write(tempFile, bytes);
                    phoneList = Files.readAllLines(path, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Files.deleteIfExists(tempFile);
                }

            } else {
                JSONArray jsonArray = messageDto.getContent().getJSONObject("userInfos").getJSONArray("varUsers");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    phoneList.add(json.getString("mobile"));
                }
            }

            //请求结果
            boolean requestResult = true;


            List<String> finalPhoneList = phoneList;
            THREAD_POOL.execute(() -> {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //状态报告
                boolean statusReport = true;
                //指定状态报告成功的手机号
                List<String> successPhonesList = Arrays.asList("18375893885", "18581456688");

                for (String p : finalPhoneList) {
                    MobileVideoMessageDto report = new MobileVideoMessageDto();
                    JSONObject id = new JSONObject();
                    JSONObject content = new JSONObject();
                    JSONObject option = new JSONObject();
                    id.put("taskId", taskId);
                    id.put("mobile", p);
                    if (statusReport) {
                        if (!successPhonesList.isEmpty() && successPhonesList.contains(p)) {
                            content.put("status", "0");
                        } else {
                            content.put("status", "1");
                        }
                    } else {
                        content.put("status", "1");
                    }
                    content.put("sendTime", "Mon Apr 09 11:17:00 CST 2021");
                    content.put("returnTime", "Mon Apr 09 11:17:00 CST 2021");
                    option.put("reqTime", System.currentTimeMillis() + "");
                    option.put("serialNum", taskIdSerial);
                    report.setId(id);
                    report.setContent(content);
                    report.setOption(option);
                    report.setSign("");

                    restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/callback/videomessage/mobile/statusreport", report, String.class);
                }


            });

            MobileVideoMessageDto result = new MobileVideoMessageDto();
            JSONObject id = new JSONObject();
            JSONObject content = new JSONObject();
            JSONObject option = new JSONObject();
            if (requestResult) {
                id.put("taskIds", new String[]{taskId});
                content.put("code", "0");
                content.put("message", "成功");
            } else {
                content.put("code", "1");
                content.put("message", "失败");
            }
            id.put("mmsId", tplId);
            id.put("productId", productId);

            option.put("resptime", System.currentTimeMillis() + "");
            option.put("serialNum", taskIdSerial);

            result.setId(id);
            result.setContent(content);
            result.setOption(option);
            result.setSign("");

            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.error("移动发送消息异常", e);
        }

        return null;
    }
}
