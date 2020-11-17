package vip.malagu.common.sdk.alipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlipayConfig {

	@Value("${alipay.url}")
	private String url;

	@Value("${alipay.signType}")
	private String signType;

	@Value("${alipay.charset}")
	private String charset;

	@Value("${alipay.appId}")
	private String appId;

	@Value("${alipay.appPrivateKey}")
	private String appPrivateKey;

	@Value("${alipay.alipayPublicKey}")
	private String alipayPublicKey;

	@Value("${alipay.notifyUrl}")
	private String notifyUrl;

	@Value("${alipay.returnUrl}")
	private String returnUrl;

	// 支付宝相关 账户id --短信使用
	@Value("${alipay.accessKeyId}")
	private String accessKeyId;

	// 支付宝相关 密钥 -- 短信使用
	@Value("${alipay.accessKeySecret}")
	private String accessKeySecret;

	// 支付宝相关 服务商
	@Value("${alipay.regionId}")
	private String regionId;

	// 支付宝 签名 -- 短信使用
	@Value("${alipay.signName}")
	private String signName;

	@Value("${order.ali.custom.pay.place}")
	public String customPlace;

	@Value("${order.custom.pay.code}")
	public String customCode;

	@Value("${order.custom.pay.name}")
	public String customName;

	@Value("${alipay.customs.publicKey}")
	private String customPublicKey;

	@Value("${alipay.customs.privateKey}")
	private String customPrivateKey;

	@Value("${alipay.partner}")
	private String partner;

	// 阿里推送相关配置
	@Value("${alipay.pushAccessKeyId}")
	private String pushAccessKeyId;

	@Value("${alipay.pushAccessKeySecret}")
	private String pushAccessKeySecret;

	@Value("${alipay.pushRegionId}")
	private String pushRegionId;

	@Value("${alipay.pushAndroidAppKey}")
	private String pushAndroidAppKey;

	@Value("${alipay.pushIosAppKey}")
	private String pushIosAppKey;

	@Value("${alipay.iosApnsEnv}")
	private String iosApnsEnv;

	// 支付宝 模版ID -- 短信使用
	public final static String REGIEST_TEMPLATECODE = "SMS_170600087"; // 注册code

	public final static String LOGIN_TEMPLATECODE = "SMS_170450797"; // 登录

	public final static String IDCATD_TEMPLATECODE = "SMS_170450798"; // 身份证验证

	public final static String PASSWORD_TEMPLATECODE = "SMS_170450794"; // 修改密码

	public final static String MESSAGE_TEMPLATECODE = "SMS_170450793"; // 信息变更

	public final static String PC_LOGIN_TEMPLATECODE = "SMS_198930374"; // 登录

	public final static String ORDER_PAY_CUSTOM_ERROR = "SMS_201715095"; // 报关错误推送

	public final static String PC_ORDER_PAY_CUSTOM_ERROR = "SMS_201650150"; // 官网报关错误推送

	public final static String COMMODITY_STOCK_WARNING = "SMS_201680613"; // 商品库存预警

	// 验证码有效期 单位：second
	public static final int CHECKCODE_VALIDITY_PERIOD = 5;

	// 用户注册验证码key前缀
	public static final String USER_REGIST_PREX = "regist_";

	// 用户登录验证码key前缀
	public static final String USER_LOGIN_PREX = "login_";

	// 用户身份验证码key前缀
	public static final String USER_IDCARD_PREX = "idCard_";

	// 用户密码验证码key前缀
	public static final String USER_PASSWORD_PREX = "password_";

	// 用户重要信息验证码key前缀
	public static final String USER_MESSAGE_PREX = "message_";

	// 用户登录验证码key前缀
	public static final String USER_PC_LOGIN_PREX = "pc_login_";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppPrivateKey() {
		return appPrivateKey;
	}

	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getCustomPlace() {
		return customPlace;
	}

	public void setCustomPlace(String customPlace) {
		this.customPlace = customPlace;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getCustomPublicKey() {
		return customPublicKey;
	}

	public void setCustomPublicKey(String customPublicKey) {
		this.customPublicKey = customPublicKey;
	}

	public String getCustomPrivateKey() {
		return customPrivateKey;
	}

	public void setCustomPrivateKey(String customPrivateKey) {
		this.customPrivateKey = customPrivateKey;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPushAccessKeyId() {
		return pushAccessKeyId;
	}

	public void setPushAccessKeyId(String pushAccessKeyId) {
		this.pushAccessKeyId = pushAccessKeyId;
	}

	public String getPushAccessKeySecret() {
		return pushAccessKeySecret;
	}

	public void setPushAccessKeySecret(String pushAccessKeySecret) {
		this.pushAccessKeySecret = pushAccessKeySecret;
	}

	public String getPushRegionId() {
		return pushRegionId;
	}

	public void setPushRegionId(String pushRegionId) {
		this.pushRegionId = pushRegionId;
	}

	public String getPushAndroidAppKey() {
		return pushAndroidAppKey;
	}

	public void setPushAndroidAppKey(String pushAndroidAppKey) {
		this.pushAndroidAppKey = pushAndroidAppKey;
	}

	public String getPushIosAppKey() {
		return pushIosAppKey;
	}

	public void setPushIosAppKey(String pushIosAppKey) {
		this.pushIosAppKey = pushIosAppKey;
	}

	public String getIosApnsEnv() {
		return iosApnsEnv;
	}

	public void setIosApnsEnv(String iosApnsEnv) {
		this.iosApnsEnv = iosApnsEnv;
	}

	/**
	 * 类型转换
	 * 
	 * @param type
	 * @return
	 */
	public static String getTypeString(Integer type) {
		switch (type) {
		case 1:
			return AlipayConfig.USER_REGIST_PREX;
		case 2:
			return AlipayConfig.USER_LOGIN_PREX;
		case 3:
			return AlipayConfig.USER_IDCARD_PREX;
		case 4:
			return AlipayConfig.USER_PASSWORD_PREX;
		case 5:
			return AlipayConfig.USER_PC_LOGIN_PREX;
		default:
			return AlipayConfig.USER_MESSAGE_PREX;
		}
	}
}
