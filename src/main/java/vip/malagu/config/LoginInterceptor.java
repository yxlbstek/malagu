package vip.malagu.config;

import org.apache.commons.lang.StringUtils;
import org.malagu.multitenant.MultitenantUtils;
import org.malagu.multitenant.domain.Organization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;
import com.bstek.bdf3.security.orm.User;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.util.TokenUtils;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @author Lynn -- 2019年11月26日 下午1:26:49
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Value("${bdf3.LoginInterceptorAnonymous}")
	private String anonymousPaths;
	
	@Value("${file.prefixPath}")
	private String filePath;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (StringUtils.isNotBlank(filePath)) {
			int index = filePath.indexOf("/");
			if (index != -1 && request.getServletPath().indexOf(filePath.substring(index + 1, filePath.length())) != -1) {
				return true;
			}
		}
		if ((StringUtils.isNotBlank(anonymousPaths) && Arrays.asList(anonymousPaths.split(",")).contains(request.getServletPath()))
				|| request.getServletPath().indexOf("static") != -1) {
			System.out.println(request.getServletPath());
			return true;
		}
		String token = request.getHeader("token");
		String orgId = request.getHeader("orgId");
		//终端类型 WEB, APP, H5
		String type = request.getHeader("type");
		if (StringUtils.isBlank(token)) {
			throw new CustomException(SystemErrorEnum.USER_NOT_LOGIN);
		}
		if (StringUtils.isBlank(orgId)) {
			throw new CustomException(SystemErrorEnum.ORG_NOT_EXIT);
		}
		
		List<Organization> orgs = MultitenantUtils.doQuery(() -> {
			return JpaUtil
				.linq(Organization.class)
				.equal("id", orgId)
				.list();
		});
		if (orgs.isEmpty()) {
			throw new CustomException(SystemErrorEnum.ORG_NOT_EXIT);
		}
		
		String username = TokenUtils.checkToken(type, orgId, token);
		List<User> users = MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil
				.linq(User.class)
				.equal("username", username)
				.equal("enabled", true)
				.list();
		});
		if (users.isEmpty()) {
			throw new CustomException(SystemErrorEnum.USER_NOT_EXIST);
		}
		if (MultitenantUtils.getLoginOrgId() != null) {
			return true;
		}
		User user = users.get(0);
		user.setOrganization(orgs.get(0));
		List<GrantedAuthority> authorities = MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil.linq(RoleGrantedAuthority.class)
					.equal("actorId", user.getUsername())
					.list();
		});
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken((UserDetails) user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
		return true;
	}

}
