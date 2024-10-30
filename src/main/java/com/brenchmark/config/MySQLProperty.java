package com.brenchmark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.db-brenchmark")
@PropertySource("classpath:mysql.properties")
public class MySQLProperty {

    private String defaultJdbcUrl;
    private Boolean autoCommit;
    private Integer connectionTimeout;
    private Integer idleTimeout;
    private Integer maxLifetime;
    private Integer minIdle;
    private Integer maxPoolSize;
    private Integer keepaliveTime;
    private String poolName;
    private Boolean readOnly;
    private String mysqlDriverClassName;
    private Boolean useSSL;
    private Boolean useUnicode;


    public String getDefaultJdbcUrl() {
		return defaultJdbcUrl;
	}

	public void setDefaultJdbcUrl(String defaultJdbcUrl) {
		this.defaultJdbcUrl = defaultJdbcUrl;
	}

	public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Integer maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getKeepaliveTime() {
        return keepaliveTime;
    }

    public void setKeepaliveTime(Integer keepaliveTime) {
        this.keepaliveTime = keepaliveTime;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getMysqlDriverClassName() {
        return mysqlDriverClassName;
    }

    public void setMysqlDriverClassName(String mysqlDriverClassName) {
        this.mysqlDriverClassName = mysqlDriverClassName;
    }

    public Boolean getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(Boolean useSSL) {
        this.useSSL = useSSL;
    }

    public Boolean getUseUnicode() {
        return useUnicode;
    }

    public void setUseUnicode(Boolean useUnicode) {
        this.useUnicode = useUnicode;
    }

}
