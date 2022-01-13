package com.mipasi.sms5g.channel.enums;

import lombok.Getter;

/**
 * 彩信通道类型
 * zhangyd
 * 2021/8/2
 */
@Getter
public enum VideoMessageChannelEnum {

    /**
     * 通道
     */
    MENG_WANG("mengwang", "梦网"),
    ZHUO_WANG("zhuowang", "卓望"),
    TELECOM("GdTelecom", "广东电信"),
    UNICOM("unicom", "联通"),
    MOBILE("mobile", "移动"),
    OTHER("other", "其他"),
    ;

    private String code;
    private String desc;

    VideoMessageChannelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取
     *
     * @param code 业务值code
     * @return 业务枚举
     */
    public static VideoMessageChannelEnum getByCode(String code) {
        for (VideoMessageChannelEnum channelTypeEnum : VideoMessageChannelEnum.values()) {
            if (channelTypeEnum.getCode().equals(code)) {
                return channelTypeEnum;
            }
        }
        return OTHER;
    }
}
