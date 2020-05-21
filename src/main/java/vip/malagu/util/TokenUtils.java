package vip.malagu.util;

import java.util.Date;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * Token相关工具类
 * @author Lynn -- 2020年5月21日 下午5:14:24
 */
public class TokenUtils {

	/**
	 * 创建token
	 * @param type 登录终端类型  WEB, APP, H5
	 * @param orgId 公司ID
	 * @param username 用户名
	 * @return
	 */
	public static String createToken(String type, String orgId, String username) {
		String dateStr = DateUtils.dateToString(new Date(), "yyyyMMddHHmmss");
		String token = EncryptUtils.DESEncode(orgId + "=" + username + "=" +  dateStr);
		String key = CacheConstant.CACHE_USER_LOGIN_PREFIX + type + "_" + orgId + "_" + username;
		RedisUtils.set(key, token);
		return token;
	}

	/**
	 * 根据Token获取用户名
	 * @param type 登录终端类型  WEB, APP, H5
	 * @param orgId 公司ID
	 * @param token 
	 * @return
	 */
	public static String checkToken(String type, String orgId, String token) {
		String tokenInfo = EncryptUtils.DESDecode(token);
		String[] infos = tokenInfo.split("=");
		if (infos.length != 3) {
			throw new CustomException(SystemErrorEnum.TOKEN_INVALID);
		}
		if (!orgId.equals(infos[0])) {
			throw new CustomException(SystemErrorEnum.ORG_NOT_MATCHING);
		}
		String key = CacheConstant.CACHE_USER_LOGIN_PREFIX + type + "_" + orgId + "_" + infos[1];
		Object cacheToken = RedisUtils.get(key);
		if (cacheToken == null || (cacheToken != null && !token.equals(cacheToken))) {
			throw new CustomException(SystemErrorEnum.TOKEN_STALE_DATED);
		}
		return infos[1];
	}

}
