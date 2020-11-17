package vip.malagu.common.sdk.wechat;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class WechatCode2SessionResponse {
	
    private String openid;
    
    private String session_key;
    
    private String unionid;
    
    private String errcode;
    
    private String errmsg;
    
}
