package vip.malagu.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.malagu.multitenant.MultitenantUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.ContextUtils;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.constants.CacheConstant;
import vip.malagu.constants.PropertyConstant;
import vip.malagu.orm.FileInfo;
import vip.malagu.orm.Message;
import vip.malagu.orm.MessageUserLink;
import vip.malagu.service.MessageService;
import vip.malagu.util.RedisUtils;

@Service
public class MessageServiceImpl implements MessageService {

	@Override
	@Transactional(readOnly = true)
	public void loadSendMsg(Page<Message> page, Criteria criteria) {
		JpaUtil
			.linq(Message.class)
			.where(criteria)
			.equal("status", 1)
			.equal("creator", ContextUtils.getLoginUsername())
			.desc("createDate")
			.paging(page);
	}

	@Override
	@Transactional(readOnly = true)
	public void loadReceiveMsg(Page<Message> page, Criteria criteria) {
		JpaUtil
			.linq(Message.class)
			.where(criteria)
			.exists(MessageUserLink.class)
				.equalProperty(PropertyConstant.MESSAGE_ID, "id")
				.equal(PropertyConstant.USER_ID, ContextUtils.getLoginUsername())
			.end()
			.desc("createDate")
			.paging(page);
		if (!page.getEntities().isEmpty()) {
			Set<String> msgIds = JpaUtil.collect(page.getEntities(), "id");
			List<MessageUserLink> links = JpaUtil
				.linq(MessageUserLink.class)
				.equal(PropertyConstant.USER_ID, ContextUtils.getLoginUsername())
				.in(PropertyConstant.MESSAGE_ID, msgIds)
				.list();
			Map<String, MessageUserLink> linkMap = JpaUtil.index(links, PropertyConstant.MESSAGE_ID);
			for (Message msg : page.getEntities()) {
				MessageUserLink link = linkMap.get(msg.getId());
				if (link != null) {
					msg.setRead(link.isRead() ? "已读" : "未读");
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void loadReceiveUsers(Page<User> page, Map<String, Object> param) {
		String msgId = (String) param.get("msgId");
		String searchKey = (String) param.get("searchKey");
		JpaUtil
			.linq(User.class)
			.addIf(searchKey)
				.or()
					.like(PropertyConstant.USERNAME, "%" + searchKey + "%")
					.like("nickname", "%" + searchKey + "%")
				.end()
			.endIf()
			.exists(MessageUserLink.class)
				.equalProperty(PropertyConstant.USER_ID, PropertyConstant.USERNAME)
				.equal(PropertyConstant.MESSAGE_ID, msgId)
			.end()
			.paging(page);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void loadUsers(Page<User> page, Criteria criteria) {
		JpaUtil
			.linq(User.class)
			.where(criteria)
			.equal("enabled", true)
			.notEqual(PropertyConstant.USERNAME, ContextUtils.getLoginUsername())
			.paging(page);
	}

	@Override
	public Integer getNotReadMsgNumber() {
		String key = CacheConstant.CACHE_USER_NOT_READ_MSG + MultitenantUtils.getLoginOrgId() + "_" +  ContextUtils.getLoginUsername();
		Object notReadMsgNumber = RedisUtils.get(key);
		return notReadMsgNumber != null ? Integer.valueOf(notReadMsgNumber.toString()) : 0;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FileInfo> loadFileInfosByMsgId(String msgId) {
		return JpaUtil
			.linq(FileInfo.class)
			.equal("businessId", msgId)
			.equal("module", "message")
			.list();
	}

	@Override
	@Transactional
	public void save(List<Message> msgs) {
		JpaUtil.save(msgs, new SmartSavePolicyAdapter() {
			
			@Override
			public boolean beforeInsert(SaveContext context) {
				if (context.getEntity() instanceof Message) {
					User user = ContextUtils.getLoginUser();
					Message msg = context.getEntity();
					msg.setId(UUID.randomUUID().toString());
					msg.setCreator(user.getUsername());
					msg.setCreatorName(user.getNickname());
					msg.setStatus(1);
				} else if (context.getEntity() instanceof User) {
					User user = context.getEntity();
					Message msg = context.getParent();
					if (user.getUsername().equals("所有人")) {
						List<User> users = JpaUtil
							.linq(User.class)
							.equal("enabled", true)
							.notEqual(PropertyConstant.USERNAME, ContextUtils.getLoginUsername())
							.list();
						if (!users.isEmpty()) {
							for (User u : users) {
								MessageUserLink link = new MessageUserLink();
								link.setId(UUID.randomUUID().toString());
								link.setUserId(u.getUsername());
								link.setMessageId(msg.getId());
								link.setRead(false);
								JpaUtil.persist(link);
								
								String key = CacheConstant.CACHE_USER_NOT_READ_MSG + MultitenantUtils.getLoginOrgId() + "_" +  u.getUsername();
								Integer number = 1;
								Object notReadNum = RedisUtils.get(key);
								if (notReadNum != null) {
									number += Integer.valueOf(notReadNum.toString());
								}
								RedisUtils.set(key, number);
							}
						}
					} else {
						MessageUserLink link = new MessageUserLink();
						link.setId(UUID.randomUUID().toString());
						link.setUserId(user.getUsername());
						link.setMessageId(msg.getId());
						link.setRead(false);
						JpaUtil.persist(link);
						
						String key = CacheConstant.CACHE_USER_NOT_READ_MSG + MultitenantUtils.getLoginOrgId() + "_" +  user.getUsername();
						Integer number = 1;
						Object notReadNum = RedisUtils.get(key);
						if (notReadNum != null) {
							number += Integer.valueOf(notReadNum.toString());
						}
						RedisUtils.set(key, number);
					}
					return false;
				} else if (context.getEntity() instanceof FileInfo) {
					FileInfo fileInfo = context.getEntity();
					Message msg = context.getParent();
					JpaUtil
						.linu(FileInfo.class)
						.set("businessId", msg.getId())
						.equal("id", fileInfo.getId())
						.update();
					return false;
				}
				return true;
			}
			
			@Override
			public boolean beforeDelete(SaveContext context) {
				if (context.getEntity() instanceof Message) {
					Message msg = context.getEntity();
					if (msg.getCreator().equals(ContextUtils.getLoginUsername())) {
						JpaUtil
							.linu(Message.class)
							.set("status", 0)
							.equal("id", msg.getId())
							.update();
					} else {
						JpaUtil
							.lind(MessageUserLink.class)
							.equal(PropertyConstant.USER_ID, ContextUtils.getLoginUsername())
							.equal(PropertyConstant.MESSAGE_ID, msg.getId())
							.delete();
					}
				}
				return false;
			}
			
		});
	}

	@Override
	@Transactional
	public void isRead(String msgId) {
		String key = CacheConstant.CACHE_USER_NOT_READ_MSG + MultitenantUtils.getLoginOrgId() + "_" +  ContextUtils.getLoginUsername();
		Object notReadNum = RedisUtils.get(key);
		if (notReadNum != null) {
			Integer number = Integer.valueOf(notReadNum.toString()) > 0 ? (Integer.valueOf(notReadNum.toString()) - 1) : 0;
			RedisUtils.set(key, number);
		}
		JpaUtil
			.linu(MessageUserLink.class)
			.set("isRead", true)
			.equal(PropertyConstant.USER_ID, ContextUtils.getLoginUsername())
			.equal(PropertyConstant.MESSAGE_ID, msgId)
			.update();
	}

}
