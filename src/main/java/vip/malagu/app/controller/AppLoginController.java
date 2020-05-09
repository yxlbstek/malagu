package vip.malagu.app.controller;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;
import com.bstek.bdf3.security.orm.User;

import vip.malagu.app.param.dto.UserLoginParam;
import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.json.JsonResult;
import vip.malagu.util.AssertUtils;
import vip.malagu.util.Constant;
import vip.malagu.util.EncryptUtils;
import vip.malagu.util.RedisUtils;
import vip.malagu.util.TokenUtils;

import org.malagu.multitenant.MultitenantUtils;
import org.malagu.multitenant.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppLoginController {

	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthCodeService yzmService;

	/**
	 * .密码登录
	 * @param loginDto 参数对象
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public JsonResult<Object> login(@RequestBody UserLoginParam loginDto, HttpServletRequest request,
			HttpServletResponse response) {
		String orgId = request.getHeader("orgId");
		AssertUtils.isNotEmptyParam(orgId, "登录公司ID");
		String type = request.getHeader("type");
		AssertUtils.isNotEmptyParam(type, "登录终端类型");
		String username = loginDto.getUsername();
		AssertUtils.isNotEmptyParam(username, "登录用户名");
		String password = loginDto.getPassword();
		AssertUtils.isNotEmptyParam(password, "登录密码");
		List<Organization> orgs = MultitenantUtils.doQuery(() -> {
			return JpaUtil.linq(Organization.class).equal("id", orgId).list();
		});
		if (!orgs.isEmpty()) {
			List<User> users = MultitenantUtils.doQuery(orgId, () -> {
				return JpaUtil.linq(User.class).equal("username", username).equal("enabled", true).list();
			});
			if (!users.isEmpty()) {
				User user = users.get(0);
				user.setOrganization(orgs.get(0));
				if (passwordEncoder.matches(password, user.getPassword())) {
					List<GrantedAuthority> authorities = MultitenantUtils.doQuery(orgId, () -> {
						return JpaUtil.linq(RoleGrantedAuthority.class).equal("actorId", username).list();
					});

					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
							(UserDetails) user, user.getPassword(), authorities);
					authRequest.getAuthorities();
					SecurityContextHolder.getContext().setAuthentication(authRequest);
					String token = TokenUtils.createToken(type, orgId, username);
					
					response.setHeader("token", token);
					Map<String, Object> result = new HashMap<>();
					result.put("token", token);
					result.put("user", user);
					return JsonResult.success(result);
				} else {
					return JsonResult.error(SystemErrorEnum.PASSWORD_WRONG);
				}
			} else {
				return JsonResult.error(SystemErrorEnum.USER_NOT_EXIST);
			}
		} else {
			return JsonResult.error(SystemErrorEnum.ORG_NOT_EXIT);
		}

	}

	/**
	 * .手机验证码登录
	 * @param loginDto 登录参数
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/phoneLogin", method = RequestMethod.POST)
	public JsonResult<Object> phoneLogin(@RequestBody UserLoginParam loginDto, HttpServletRequest request,
			HttpServletResponse response) {
		String orgId = request.getHeader("orgId");
		AssertUtils.isNotEmptyParam(orgId, "登录公司ID");
		String type = request.getHeader("type");
		AssertUtils.isNotEmptyParam(type, "登录终端类型");
		String codeType = request.getHeader("codeType");
		AssertUtils.isNotEmptyParam(codeType, "验证码类型");
		String phone = loginDto.getPhone();
		AssertUtils.isNotEmptyParam(phone, "登录手机号");
		String code = loginDto.getCode();
		AssertUtils.isNotEmptyParam(code, "登录验证码");
		List<Organization> orgs = MultitenantUtils.doQuery(() -> {
			return JpaUtil.linq(Organization.class).equal("id", orgId).list();
		});
		if (!orgs.isEmpty()) {
			List<User> users = MultitenantUtils.doQuery(orgId, () -> {
				//##########################
				//####  待扩展, 无手机号字段(phone)
				//##########################
				return JpaUtil.linq(User.class).equal("phone", phone).equal("enabled", true).list();
			});
			if (!users.isEmpty()) {
				// 验证码校验
				AuthCodeParam yzmParam = new AuthCodeParam();
				yzmParam.setPhone(phone);
				yzmParam.setCode(code);
				yzmParam.setCodeType(Integer.valueOf(codeType));
				yzmService.validateCode(yzmParam);
				
				User user = users.get(0);
				user.setOrganization(orgs.get(0));
				List<GrantedAuthority> authorities = MultitenantUtils.doQuery(orgId, () -> {
					return JpaUtil.linq(RoleGrantedAuthority.class).equal("actorId", user.getUsername()).list();
				});
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken((UserDetails) user, user.getPassword(), authorities);
				SecurityContextHolder.getContext().setAuthentication(authRequest);
				
				String token = TokenUtils.createToken(type, orgId, user.getUsername());// 生成token
				response.setHeader("token", token);
				Map<String, Object> result = new HashMap<>();
				result.put("token", token);
				result.put("user", users.get(0));
				return JsonResult.success(result);
			}
			return JsonResult.error(SystemErrorEnum.PHONE_NOT_EXIST);
		}
		return JsonResult.error(SystemErrorEnum.ORG_NOT_EXIT);
	}

	/**
	 * .登出
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path = "/logout", method = RequestMethod.POST)
	public JsonResult<Object> logout(HttpServletRequest request) throws Exception {
		String token = request.getHeader("token");
		String type = request.getHeader("type");
		String tokenInfo = EncryptUtils.DESDecode(token);
		String[] infos = tokenInfo.split("=");
		if (infos.length != 3) {
			return JsonResult.error(SystemErrorEnum.TOKEN_INVALID);
		}
		String key = Constant.CACHE_USER_LOGIN_PREFIX + type + "_" + infos[0] + "_" + infos[1];
		RedisUtils.delete(key);
		return JsonResult.success();
	}

}
