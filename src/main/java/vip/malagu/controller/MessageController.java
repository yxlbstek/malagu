package vip.malagu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.FileInfo;
import vip.malagu.orm.Message;
import vip.malagu.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	@DataProvider
	public void loadSendMsg(Page<Message> page, Criteria criteria) {
		messageService.loadSendMsg(page, criteria);
	}
	
	@DataProvider
	public void loadReceiveMsg(Page<Message> page, Criteria criteria) {
		messageService.loadReceiveMsg(page, criteria);
	}
	
	@DataProvider
	public void loadReceiveUsers(Page<User> page, Map<String, Object> param) {
		messageService.loadReceiveUsers(page, param);
	}
	
	@DataProvider
	public List<FileInfo> loadFileInfosByMsgId(String msgId) {
		return messageService.loadFileInfosByMsgId(msgId);
	}

	@DataResolver
	public void save(List<Message> msgs) {
		messageService.save(msgs);
	}
	
	@Expose
	public void isRead(String msgId) {
		messageService.isRead(msgId);
	}
	
	@Expose
	public Integer getNotReadMsgNumber() {
		return messageService.getNotReadMsgNumber();
	}

}
