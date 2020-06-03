package vip.malagu.custom.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import vip.malagu.json.JsonResult;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(CustomException.class)
	public JsonResult<Object> handlerException(CustomException exception) {
		return JsonResult.error(exception.getMessage(), exception.getStatus());
	}
	
}