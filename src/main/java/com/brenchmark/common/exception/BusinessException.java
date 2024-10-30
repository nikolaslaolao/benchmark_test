package com.brenchmark.common.exception;

import com.brenchmark.common.constant.MessageCode;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
    	super();
    	this.code = code;
    	this.message = message;
    }

    public BusinessException(MessageCode messageCode) {
        this.code = messageCode.getCode();
        this.message = messageCode.getText();
    }
    
    public BusinessException(BusinessException e) {
        this.code = e.code;
        this.message = e.message;
    }
    
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

	public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

	public String toString() {
        return "code = " + this.code + ", message = " + this.message;
    }
}
