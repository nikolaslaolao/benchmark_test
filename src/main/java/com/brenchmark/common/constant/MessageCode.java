package com.brenchmark.common.constant;

import org.springframework.http.HttpStatus;

public enum MessageCode {
	
	MSG_10000(10000,HttpStatus.INTERNAL_SERVER_ERROR,"Column Number is out of bound"),	
	
	;
	
	public static MessageCode getMessageCodeByCode(Integer code){
		for(MessageCode m:MessageCode.values()){
			if(m.getCode()==code){
				return m;
			}
		}
		return null;
	}
	
	public static MessageCode getMessageCodeByText(String text){
		for(MessageCode m:MessageCode.values()){
			if(m.getText().equals(text)){
				return m;
			}
		}
		return null;
	}
	
	public static String getText(Integer code){
		for(MessageCode m:MessageCode.values()){
			if(m.getCode()==code){
				return m.getText();
			}
		}
		return null;
	}
	
	
	public static void main(String[] args){
		System.out.println(getText(19008));
		System.out.println(getMessageCodeByCode(19008));
	}
	
	
    private int code;  
    private HttpStatus status;
    private String text;
    
    private MessageCode(int code, HttpStatus status,String text) {
    	this.code = code;      
        this.status = status;
        this.text = text;
    }
    
    
    public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
