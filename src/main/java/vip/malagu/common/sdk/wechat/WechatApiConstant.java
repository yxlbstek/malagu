package vip.malagu.common.sdk.wechat;

public class WechatApiConstant {
	
	/**
	 * 接口授权
	 */
	public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * js获取用户授权
	 */
	public static final String GET_JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取用户unionId
	 */
    public static final String CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    /**
     * 发送订阅消息
     */
    public static final String SUBSCRIBE_MESSAGE_SEND = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";

    /**
     * 统一消息发送
     */
    public static final String UNIFORM_SEND = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
}
