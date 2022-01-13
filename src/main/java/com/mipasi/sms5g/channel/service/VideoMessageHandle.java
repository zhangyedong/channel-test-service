package com.mipasi.sms5g.channel.service;

import com.mipasi.sms5g.channel.enums.VideoMessageChannelEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zhangyd
 * @Description: 彩信通道
 * @date 2022/1/6
 */
@Service
@AllArgsConstructor
public class VideoMessageHandle {

    private final MengWangService mengWangService;
    private final UnicomService unicomService;
    private final MobileService mobileService;

    /**
     * 创建模板
     *
     * @return 结果
     */
    public String addTemplate(VideoMessageChannelEnum channelEnum, String request) {
        String response;
        switch (channelEnum) {
            case MENG_WANG:
                response = mengWangService.addTemplate(request);
                break;
            case UNICOM:
                response = unicomService.addTemplate(request);
                break;
            case MOBILE:
                response = mobileService.addTemplate(request);
                break;
            default:
                throw new RuntimeException("通道服务匹配异常...");
        }
        return response;
    }

    /**
     * 上传文件资源
     *
     * @return 结果
     */
    public String uploadResource(VideoMessageChannelEnum channelEnum, String request) {
        String response;
        switch (channelEnum) {
            case MOBILE:
                response = mobileService.uploadResource(request);
                break;
            default:
                throw new RuntimeException("通道服务匹配异常...");
        }
        return response;
    }

    /**
     * 发送消息
     *
     * @return 结果
     */
    public String sendMessage(VideoMessageChannelEnum channelEnum, String request, Boolean staticTemplate) {
        String response;
        switch (channelEnum) {
            case MENG_WANG:
                response = mengWangService.sendMessage(request);
                break;
            case UNICOM:
                response = unicomService.sendMessage(request);
                break;
            case MOBILE:
                response = mobileService.sendMessage(request, staticTemplate);
                break;
            default:
                throw new RuntimeException("通道服务匹配异常...");
        }
        return response;
    }
}
