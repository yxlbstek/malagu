package vip.malagu.common.sdk.wechat;

import lombok.Data;

@Data
public class WechatDecryptPhoneResponse {
	
    /**
     * 手机号（可能带区号）
     */
    private String phoneNumber;

    /**
     * 纯手机号
     */
    private String purePhoneNumber;

    /**
     * 国家区号
     */
    private String countryCode;

    /**
     * 水印
     */
    private Watermark watermark;
}
