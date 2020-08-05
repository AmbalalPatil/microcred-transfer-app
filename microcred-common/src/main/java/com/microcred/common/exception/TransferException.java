package com.microcred.common.exception;

/**
 * @author Ambalal Patil
 *
 */
public class TransferException extends Exception {

	private static final long serialVersionUID = -5351488822224967176L;
	
	private ResponseCodeEnum errorCode;
	private String message;
	
	public TransferException(ResponseCodeEnum errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public ResponseCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ResponseCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
