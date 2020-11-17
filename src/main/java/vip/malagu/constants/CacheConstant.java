package vip.malagu.constants;

/**
 * Redis缓存相关
 * 
 * @author Lynn -- 2020年5月21日 下午4:59:42
 */
public class CacheConstant {

	private CacheConstant() {
	}

	/**
	 * 终端登录缓存Key 前缀
	 */
	public static final String CACHE_USER_LOGIN_PREFIX = "user_login_token_";

	/**
	 * 用户未读邮件信息数量缓存Key 前缀
	 */
	public static final String CACHE_USER_NOT_READ_MSG = "user_not_read_msg_";

	/**
	 * 微信接口请求凭证
	 */
	public static final String WECHAT_ACCESS_TOKEN = "WECHAT_ACCESS_TOKEN";

	/**
	 * JS获取用户授权
	 */
	public static final String WECHAT_JS_TICKET = "WECHAT_JS_TICKET";
	
	/**
     * 小程序码分享
     */
    public static final String WECHAT_SHARE = "WECHAT_SHARE";
    
	/**
     * 支付宝-支付成功后回调存储的支付流水号
     */
    public static final String CACHE_PAY_SUCCESS_TRADENO_ALI_LIST = "PAY_SUCCESS_TRADENO_ALI_LIST";
    
    /**
     * 微信-支付成功后回调存储的支付流水号
     */
    public static final String CACHE_PAY_SUCCESS_TRADENO_WX_LIST = "PAY_SUCCESS_TRADENO_WX_LIST";

}
