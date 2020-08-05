package com.microcred.common.response;

/**
 * @author Ambalal Patil
 */
public class MicroCredResponse<T> {

	private String type;
	private String code;
	private String message;
	private T data;
	
	public MicroCredResponse() {
	}
	
	public MicroCredResponse(String type, String code, String message, T data) {
		super();
		this.type = type;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
