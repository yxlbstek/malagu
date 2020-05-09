package vip.malagu.uflo.impl;


import org.hibernate.SessionFactory;
import org.malagu.multitenant.MultitenantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.uflo.env.EnvironmentProvider;

@Controller
public class UfloEnvironmentProvider implements EnvironmentProvider {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("platformTransactionManager")
	private PlatformTransactionManager platformTransactionManager;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}

	public String getCategoryId() {
		return MultitenantUtils.getLoginOrgId();
	}

	public String getLoginUser() {
		return ContextUtils.getLoginUsername();
	}

}
