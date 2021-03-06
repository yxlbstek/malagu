package vip.malagu.app.controller;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;
import com.bstek.bdf3.security.orm.User;

import vip.malagu.app.param.dto.UserLoginParam;
import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.constants.CacheConstant;
import vip.malagu.constants.ErrorTipConstant;
import vip.malagu.constants.PropertyConstant;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.json.JsonResult;
import vip.malagu.util.AssertUtils;
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
		AssertUtils.isNotEmptyParam(orgId, ErrorTipConstant.LOGIN_ORG_ID_NOT_EMPTY);
		String type = request.getHeader("type");
		AssertUtils.isNotEmptyParam(type, ErrorTipConstant.LOGIN_TYPE_NOT_EMPTY);
		String username = loginDto.getUsername();
		AssertUtils.isNotEmptyParam(username, ErrorTipConstant.LOGIN_USERNAME_NOT_EMPTY);
		String password = loginDto.getPassword();
		AssertUtils.isNotEmptyParam(password, ErrorTipConstant.LOGIN_PSW_NOT_EMPTY);
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
					
					response.setHeader(PropertyConstant.TOKEN, token);
					Map<String, Object> result = new HashMap<>();
					result.put(PropertyConstant.TOKEN, token);
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
		AssertUtils.isNotEmptyParam(orgId, ErrorTipConstant.LOGIN_ORG_ID_NOT_EMPTY);
		String type = request.getHeader("type");
		AssertUtils.isNotEmptyParam(type, ErrorTipConstant.LOGIN_TYPE_NOT_EMPTY);
		String codeType = request.getHeader("codeType");
		AssertUtils.isNotEmptyParam(codeType, ErrorTipConstant.MSG_CODE_TYPE_NOT_EMPTY);
		String phone = loginDto.getPhone();
		AssertUtils.isNotEmptyParam(phone, ErrorTipConstant.USER_PHONE_NOT_EMPTY);
		String code = loginDto.getCode();
		AssertUtils.isNotEmptyParam(code, ErrorTipConstant.MSG_CODE_NOT_EMPTY);
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
				response.setHeader(PropertyConstant.TOKEN, token);
				Map<String, Object> result = new HashMap<>();
				result.put(PropertyConstant.TOKEN, token);
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
	 */
	@ResponseBody
	@RequestMapping(path = "/logout", method = RequestMethod.POST)
	public JsonResult<Object> logout(HttpServletRequest request) {
		String token = request.getHeader(PropertyConstant.TOKEN);
		String type = request.getHeader("type");
		String tokenInfo = EncryptUtils.decodeByDES(token);
		String[] infos = tokenInfo.split("=");
		if (infos.length != 3) {
			return JsonResult.error(SystemErrorEnum.TOKEN_INVALID);
		}
		String key = CacheConstant.CACHE_USER_LOGIN_PREFIX + type + "_" + infos[0] + "_" + infos[1];
		RedisUtils.delete(key);
		return JsonResult.success();
	}

}
