package vip.malagu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.annotation.DataProvider;

@Controller
public class UserMaintainController {

	@DataProvider
	@Transactional(readOnly = true)
	public User loadLoginUser() {
		return ContextUtils.getLoginUser();
	}

}
