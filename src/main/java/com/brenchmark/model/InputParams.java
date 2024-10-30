package com.brenchmark.model;

import java.util.Map;
import java.util.HashMap;

public class InputParams {
	private String file;
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public InputParams() {
		this(new Builder());
	}
	public InputParams(Builder builder) {
		this.file=builder.file;
	}


	public static class Builder { 
		private String file;

		public Builder file(String file) {
			this.file=file;
			return this;
		}
		public InputParams build(){
			return new InputParams(this);
		}
	}
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("file",this.file);
		return map;
	}
}
