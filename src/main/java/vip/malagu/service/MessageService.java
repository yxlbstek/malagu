package vip.malagu.service;

import java.util.List;
import java.util.Map;

import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.FileInfo;
import vip.malagu.orm.Message;

public interface MessageService {

	void save(List<Message> msgs);

	void loadSendMsg(Page<Message> page, Criteria criteria);

	void loadReceiveMsg(Page<Message> page, Criteria criteria);

	void isRead(String msgId);

	void loadReceiveUsers(Page<User> page, Map<String, Object> param);

	List<FileInfo> loadFileInfosByMsgId(String msgId);

	Integer getNotReadMsgNumber();

}