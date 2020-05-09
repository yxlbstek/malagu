package vip.malagu.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.json.JsonResult;

@RestController
@RequestMapping("/app/code")
public class AuthCodeController {

	@Autowired
	private AuthCodeService authCodeService;

	/**
	 * .发送验证码
	 * @param AuthCodeParam param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	public JsonResult<Object> getCode(@RequestBody AuthCodeParam param) {
		authCodeService.getCode(param);
		return JsonResult.success();
	}

	/**
	 * .验证验证码是否一致
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	public JsonResult<Object> validateCode(@RequestBody AuthCodeParam param) {
		return JsonResult.success(authCodeService.validateCode(param));
	}

}
