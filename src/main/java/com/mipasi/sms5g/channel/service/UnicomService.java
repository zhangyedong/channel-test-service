package com.mipasi.sms5g.channel.service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyd
 * @Description: 联通服务
 * @date 2022/1/6
 */
@Service
@Slf4j
public class UnicomService {

    @Resource
    private RestTemplate restTemplate;

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 100,
            60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024), new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());

    public String addTemplate(String request) {
        String tplId = RandomUtil.randomNumbers(7);
        try {

            THREAD_POOL.execute(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                JSONObject response = new JSONObject();
                JSONObject responseDetails = new JSONObject();
                responseDetails.put("cuState", "80");
                responseDetails.put("cmState", "80");
                responseDetails.put("ctState", "80");

                response.put("mmsId", tplId);
                response.put("state", 80);
                response.put("reason", "成功");
                response.put("details", responseDetails);

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/callback/videomessage/unicom/auditreport", response, String.class);
            });

            JSONObject result = new JSONObject();
            result.put("code", "0000");
            result.put("msg", "成功");
            result.put("traceId", IdUtil.fastSimpleUUID());
            result.put("mmsId", tplId);

            return result.toJSONString();
        } catch (Exception e) {
            log.error("添加联通模板异常", e);
        }
        return null;
    }

    public String sendMessage(String request) {
        log.info("联通发送消息：request：{}", request);
        JSONObject json = JSON.parseObject(request);
        List<String> phoneList = new ArrayList<>();
        JSONArray jsonArray = json.getJSONArray("phones");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonInner = jsonArray.getJSONObject(i);
            phoneList.add(jsonInner.getString("phone"));
        }
        String taskId = RandomUtil.randomNumbers(7);
        try {
            //请求结果
            boolean requestResult = true;

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

                JSONObject report = new JSONObject();
                for (String p : phoneList) {
                    report.put("transId", taskId);
                    report.put("phone", p);
                    if (statusReport) {
                        if (!successPhonesList.isEmpty() && successPhonesList.contains(p)) {
                            report.put("state", "80");
                        } else {
                            report.put("state", "99");
                        }
                    } else {
                        report.put("state", "99");
                    }
                }

                restTemplate.postForEntity("http://127.0.0.1:9500/api/v1/callback/videomessage/unicom/statusreport", report, String.class);

            });


            JSONObject result = new JSONObject();
            result.put("traceId", IdUtil.fastSimpleUUID());
            if (requestResult) {
                result.put("code", "000");
                result.put("msg", "成功");
                result.put("transId", taskId);
            } else {
                result.put("code", "-1");
                result.put("msg", "请求失败");
            }

            return result.toJSONString();
        } catch (Exception e) {
            log.error("联通发送彩信异常", e);
        }

        return null;
    }
}
