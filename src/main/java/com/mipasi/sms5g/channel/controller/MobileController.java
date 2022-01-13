package com.mipasi.sms5g.channel.controller;

import com.mipasi.sms5g.channel.service.MobileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangyd
 * @Description: 移动
 * @date 2022/1/6
 */
@RestController
public class MobileController {

    @Resource
    private MobileService mobileService;

    /**
     * 创建模板
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping(value = {"/api/v1/saveMms/save", "/api/v1/saveMms/variable/save"})
    public String addTemplate(@RequestBody String request) {
        return mobileService.addTemplate(request);
    }

    /**
     * 上传资源
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/api/v1/SyncResource/mms/var/add/resources")
    public String uploadResource(@RequestBody String request) {
        return mobileService.uploadResource(request);
    }

    /**
     * 发送静态消息
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/api/v1/sendTask/sendMms")
    public String sendStaticMessage(@RequestBody String request) {
        return mobileService.sendMessage(request, true);
    }

    /**
     * 发送变量消息
     *
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/api/v1/sendTask/variableMms/send")
    public String sendDynamicMessage(@RequestBody String request) {
        return mobileService.sendMessage(request, false);
    }
}
