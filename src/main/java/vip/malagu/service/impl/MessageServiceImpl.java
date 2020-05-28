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
import vip.malagu.orm.FileInfo;
import vip.malagu.orm.Message;
import vip.malagu.orm.MessageUserLink;
import vip.malagu.service.MessageService;
import vip.malagu.util.FileUtils;
import vip.malagu.util.RedisUtils;

@Service
public class MessageServiceImpl implements MessageService {

	@Override
	@Transactional(readOnly = true)
	public void loadSendMsg(Page<Message> page, Criteria criteria) {
		JpaUtil
			.linq(Message.class)
			.where(criteria)
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
				.equalProperty("messageId", "id")
				.equal("userId", ContextUtils.getLoginUsername())
			.end()
			.desc("createDate")
			.paging(page);
		if (!page.getEntities().isEmpty()) {
			Set<String> msgIds = JpaUtil.collectId(page.getEntities());
			List<MessageUserLink> links = JpaUtil
				.linq(MessageUserLink.class)
				.equal("userId", ContextUtils.getLoginUsername())
				.in("messageId", msgIds)
				.list();
			Map<String, MessageUserLink> linkMap = JpaUtil.index(links, "messageId");
			for (Message msg : page.getEntities()) {
				MessageUserLink link = linkMap.get(msg.getId());
				if (link != null) {
					msg.setRead(link.isRead());
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void loadReceiveUsers(Page<User> page, Map<String, Object> param) {
		Integer msgId = Integer.valueOf(param.get("msgId").toString());
		String searchKey = (String) param.get("searchKey");
		JpaUtil
			.linq(User.class)
			.addIf(searchKey)
				.or()
					.like("useranme", "%" + searchKey + "%")
					.like("nickname", "%" + searchKey + "%")
				.end()
			.endIf()
			.exists(MessageUserLink.class)
				.equalProperty("userId", "id")
				.equal("messageId", msgId)
			.end()
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
				} else if (context.getEntity() instanceof User) {
					User user = context.getEntity();
					Message msg = context.getParent();
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
					JpaUtil
						.lind(MessageUserLink.class)
						.equal("userId", ContextUtils.getLoginUsername())
						.equal("messageId", msg.getId())
						.delete();
					FileUtils.deleteModuleFile(msg.getId(), "message");
				} else if (context.getEntity() instanceof User) {
					User user = context.getEntity();
					Message msg = context.getParent();
					JpaUtil
						.lind(MessageUserLink.class)
						.equal("userId", user.getUsername())
						.equal("messageId", msg.getId())
						.delete();
					return false;
				} else if (context.getEntity() instanceof FileInfo) {
					FileInfo fileInfo = context.getEntity();
					FileUtils.deleteFile(fileInfo);
					return false;
				}
				return true;
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
			.equal("userId", ContextUtils.getLoginUsername())
			.equal("messageId", msgId)
			.update();
	}

}
