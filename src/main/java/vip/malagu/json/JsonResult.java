package vip.malagu.json;

import java.io.Serializable;

import vip.malagu.custom.exception.ICustomException;
import vip.malagu.enums.SystemErrorEnum;

public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -3842011583936904824L;

	private SubJsonResult subJsonResult;
    
    private T data;

    public JsonResult() {}

    public SubJsonResult getSubJsonResult() {
		return subJsonResult;
	}

	public void setSubJsonResult(SubJsonResult subJsonResult) {
		this.subJsonResult = subJsonResult;
	}

	public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 创建返回值
     * @param data
     * @param subJsonResult
     * @param <T>
     * @return
     */
    public static <T> JsonResult<T> createResult(T data, SubJsonResult subJsonResult) {
        JsonResult<T> jsonResult = new JsonResult<T>();
        jsonResult.setData(data);
        jsonResult.setSubJsonResult(subJsonResult);
        return jsonResult;
    }

    /**
     * 当正确时返回的值
     * @param data
     * @return
     */
    public static <T> JsonResult<T> success(T data){
        return createResult(data, SubJsonResult.createDefault(SystemErrorEnum.SUCCESS.getMessage()));
    }

    /**
     * 当正确时返回的值
     * @return
     */
    public static <T> JsonResult<T> success(){
        return success(null);
    }

    /**
     * 当错误时返回的值
     * @param code
     * @param msg
     * @return
     */
    public static <T> JsonResult<T> error(String msg, String status){
        return JsonResult.createResult(null, SubJsonResult.createDefault(msg, status));
    }

    /**
     * 当错误时返回的值
     * @param codeEnum
     * @return
     */
    public static <T> JsonResult<T> error(ICustomException statusEnum){
        return error(statusEnum.getMessage(), statusEnum.getStatus());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("JsonResult{");
        sb.append("result=").append(this.subJsonResult);
        sb.append(", data=").append(this.data);
        sb.append('}');
        return sb.toString();
    }

}