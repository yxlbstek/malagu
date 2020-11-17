package vip.malagu.app.service;

public interface AppNoticService {

	/**
	 * 发布通知给单个用户
	 * @param username 接收人
	 * @param content 发送内容
	 * @return
	 */
	boolean push(String username, String content);
	
	/**
	 * 发布通知给所有人
	 * @param content 发送内容
	 * @return
	 */
	boolean pushAll(String content);

}
