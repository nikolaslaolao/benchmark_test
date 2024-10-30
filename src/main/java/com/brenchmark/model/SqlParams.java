package com.brenchmark.model;

import java.util.HashMap;
import java.util.Map;

import com.brenchmark.common.constant.ColumnType;
import com.brenchmark.common.constant.SqlType;

public class SqlParams {
	private Integer concurrency;
	private Integer totalNum;
	private String user;
	private String password;
	private String host;
	private String database;
	private String tableName;
	private SqlType sqlType;
	private ColumnType columnType;
	private Integer columnNum;
	private Boolean addCreateTime;

	public Integer getConcurrency() {
		return concurrency;
	}
	public void setConcurrency(Integer concurrency) {
		this.concurrency = concurrency;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public SqlType getSqlType() {
		return sqlType;
	}
	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}
	public ColumnType getColumnType() {
		return columnType;
	}
	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	public Integer getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}
	public Boolean getAddCreateTime() {
		return addCreateTime;
	}
	public void setAddCreateTime(Boolean addCreateTime) {
		this.addCreateTime = addCreateTime;
	}
	
	
	public SqlParams() {
		this(new Builder());
	}
	public SqlParams(Builder builder) {
		this.concurrency=builder.concurrency;
		this.totalNum=builder.totalNum;
		this.user=builder.user;
		this.password=builder.password;
		this.host=builder.host;
		this.database=builder.database;
		this.tableName=builder.tableName;
		this.sqlType=builder.sqlType;
		this.columnType=builder.columnType;
		this.columnNum=builder.columnNum;
		this.addCreateTime=builder.addCreateTime;
	}


	public static class Builder { 
		private Integer concurrency;
		private Integer totalNum;
		private String user;
		private String password;
		private String host;
		private String database;
		private String tableName;
		private SqlType sqlType;
		private ColumnType columnType;
		private Integer columnNum;
		private Boolean addCreateTime;

		public Builder concurrency(Integer concurrency) {
			this.concurrency=concurrency;
			return this;
		}
		public Builder totalNum(Integer totalNum) {
			this.totalNum=totalNum;
			return this;
		}
		public Builder user(String user) {
			this.user=user;
			return this;
		}
		public Builder password(String password) {
			this.password=password;
			return this;
		}
		public Builder host(String host) {
			this.host=host;
			return this;
		}
		public Builder database(String database) {
			this.database=database;
			return this;
		}
		public Builder tableName(String tableName) {
			this.tableName=tableName;
			return this;
		}
		public Builder sqlType(SqlType sqlType) {
			this.sqlType=sqlType;
			return this;
		}
		public Builder columnType(ColumnType columnType) {
			this.columnType=columnType;
			return this;
		}
		public Builder columnNum(Integer columnNum) {
			this.columnNum=columnNum;
			return this;
		}
		public Builder addCreateTime(Boolean addCreateTime) {
			this.addCreateTime=addCreateTime;
			return this;
		}


		public SqlParams build(){
			return new SqlParams(this);
		}
	}
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("concurrency",this.concurrency);
		map.put("totalNum",this.totalNum);
		map.put("user",this.user);
		map.put("password",this.password);
		map.put("host",this.host);
		map.put("database",this.database);
		map.put("tableName",this.tableName);
		map.put("sqlType",this.sqlType);
		map.put("columnType",this.columnType);
		map.put("columnNum",this.columnNum);
		map.put("addCreateTime",this.addCreateTime);
		return map;
	}
}
