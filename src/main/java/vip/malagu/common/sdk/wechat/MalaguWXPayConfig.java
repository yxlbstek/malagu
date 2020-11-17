package vip.malagu.common.sdk.wechat;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class MalaguWXPayConfig extends WXPayConfig {

    @Value("${wechat.appId}")
    private String appID;

    @Value("${wechat.mchId}")
    private String mchID;

    @Value("${wechat.key}")
    private String key;

    @Value("${wechat.notifyUrl}")
    private String notifyUrl;
    
    @Value("${wechat.cert}")
    private String cert;
    
    @Value("${wechat.partnerId}")
    private String partnerId;
    
    @Value("${wechat.partnerKey}")
    private String partnerKey;
    
    @Value("${order.custom.pay.place}")
    private String customPlace;

    @Value("${order.custom.pay.code}")
    private String customCode;
    
    @Value("${wechat.mini.appId}")
    private String miniAppId;

    @Value("${wechat.mini.secret}")
    private String miniSecret;

    @Value("${wechat.mini.programState}")
    private String programState;
    
    public String getMchID() {
		return mchID;
	}

	public String getKey() {
		return key;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public String getCustomPlace() {
		return customPlace;
	}

	public String getCustomCode() {
		return customCode;
	}

	public String getCert() {
		return cert;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public String getMiniAppId() {
		return miniAppId;
	}

	public String getMiniSecret() {
		return miniSecret;
	}

	public String getProgramState() {
		return programState;
	}

	@Override
    InputStream getCertStream() {
    	File file = new File(cert);
    	InputStream certStream = null;
		try {
			certStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CustomException("获取证书异常", SystemErrorEnum.FAIL.getStatus());
		}
        return certStream;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

	@Override
	String getAppID() {
		return appID;
	}
}
