package vip.malagu.custom.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1235196442382516631L;

	private String status;
	
	private ICustomException exception;

	public CustomException(ICustomException exception) {
		super(exception.getMessage());
		this.status = exception.getStatus();
		this.exception = exception;
	}

	public CustomException(String message, String status) {
		super(message);
		this.status = status;
	}

	public CustomException(String message, Throwable cause, String status, ICustomException exception) {
		super(message, cause);
		this.status = status;
		this.exception = exception;
	}

	public CustomException(Throwable cause, String status, ICustomException exception) {
		super(cause);
		this.status = status;
		this.exception = exception;
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			String status, ICustomException exception) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
		this.exception = exception;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ICustomException getException() {
		return exception;
	}

	public void setException(ICustomException exception) {
		this.exception = exception;
	}
	
}