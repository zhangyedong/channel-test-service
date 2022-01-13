package com.mipasi.sms5g.channel.controller;

import com.mipasi.sms5g.channel.service.MengWangService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangyd
 * @Description: 梦网
 * @date 2022/1/6
 */
@RestController
public class MengWangController {

    @Resource
    private MengWangService mengWangService;

    /**
     * 创建模板
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/rms/v3/std/tpl/tpl_upload")
    public String addTemplate(@RequestBody String request) {
        return mengWangService.addTemplate(request);
    }

    /**
     * 发送消息
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/rms/v3/std/msg/uni_tpl_send")
    public String sendMessage(@RequestBody String request) {
        return mengWangService.sendMessage(request);
    }
}
