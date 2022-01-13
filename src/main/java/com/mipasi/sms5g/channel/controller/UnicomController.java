package com.mipasi.sms5g.channel.controller;

import com.mipasi.sms5g.channel.service.UnicomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangyd
 * @Description: 联通
 * @date 2022/1/6
 */
@RestController
public class UnicomController {

    @Resource
    private UnicomService unicomService;

    @PostMapping("/api/mms/auditSubmit")
    public String addTemplate(@RequestBody String request) {
        return unicomService.addTemplate(request);
    }

    @PostMapping(value = {"/api/send", "/api/paramSend"})
    public String sendMessage(@RequestBody String request) {
        return unicomService.sendMessage(request);
    }
}
