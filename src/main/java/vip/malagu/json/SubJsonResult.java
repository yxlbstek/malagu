package vip.malagu.json;

import java.io.Serializable;

public class SubJsonResult implements Serializable {

	private static final long serialVersionUID = -8786919741253701614L;

	private String message;
    
    private String status;

    public SubJsonResult() {
    	//构造方法
    }

    public static SubJsonResult createDefault(String message) {
        return createDefault(message, "200");
    }

    public static SubJsonResult createDefault(String message, String status) {
        SubJsonResult subJsonResult = new SubJsonResult();
        subJsonResult.setMessage(message);
        subJsonResult.setStatus(status);
        return subJsonResult;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder("SubJsonResult{");
        sb.append("message='").append(this.message).append('\'');
        sb.append(", status='").append(this.status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
