package com.brenchmark.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.brenchmark.common.constant.SqlType;
import com.brenchmark.config.MySQLProperty;
import com.brenchmark.model.InputParams;
import com.brenchmark.model.SqlParams;
import jakarta.annotation.PostConstruct;

@Component
public class MySQLBrenchMark {
	private static Logger log = LoggerFactory.getLogger(MySQLBrenchMark.class);
	private static AtomicInteger COUNT = new AtomicInteger(0);
	public static String DATA_SOURCE_URL="";
	
	@Autowired
	private MySQLProperty mySQLProperty;

	@PostConstruct
	private void databaseSource() {
		DATA_SOURCE_URL=mySQLProperty.getDefaultJdbcUrl();
	}
	
    public static void insertSqlFile(InputParams input) {
    	try {
    		List<String> isEmptyValues=input.toMap().entrySet().stream().filter(entry-> entry.getValue().equals("")).map(Map.Entry::getKey).collect(Collectors.toList());
    		if(isEmptyValues.size()>0) {
    			isEmptyValues.stream().map(a->a+" cannot be left empty.").forEach(System.out::println);
    			System.exit(0);
    		}
    		input.toMap().entrySet().forEach(System.out::println);
    		List<String> sqlList = Files.readAllLines(Paths.get(input.getFile()));
    		Map<String, String> sqlMap=sqlList.stream().collect(Collectors.toMap(a->a.split("=")[0], a->a.split("=")[1]));
    		SqlParams sqlParam=JSON.parseObject(JSON.toJSONString(sqlMap),SqlParams.class);
    		String sqlImplement=tableCreation(sqlParam);
    		String url = String.format(DATA_SOURCE_URL, sqlParam.getHost(),sqlParam.getDatabase());
    		ExecutorService executorService = Executors.newFixedThreadPool(sqlParam.getConcurrency());
    		LocalDateTime startTime=LocalDateTime.now();
    		ScheduledExecutorService progressScheduler = Executors.newSingleThreadScheduledExecutor();
            progressScheduler.scheduleAtFixedRate(() -> {
                long s = Duration.between(startTime, LocalDateTime.now()).toSeconds();
                int currentNum=COUNT.get()>=sqlParam.getTotalNum()?sqlParam.getTotalNum():COUNT.get();
                System.out.println("eclapsed->" + s + "s, finished->" + currentNum);
            }, 0, 1, TimeUnit.SECONDS);
            for (int i = 0; i < sqlParam.getConcurrency(); i++) {
                executorService.execute(() -> {
                    try (Connection conn = DriverManager.getConnection(url, sqlParam.getUser(),sqlParam.getPassword())){
                        while (COUNT.getAndIncrement() < sqlParam.getTotalNum()) {
            				PreparedStatement pstmt= conn.prepareStatement(sqlImplement);
            		        pstmt.executeUpdate();
                        }
                    } catch (SQLException e) {
                    	log.error(e.toString());  
                    	System.exit(0);
                    }
                });
                if(COUNT.get()>=sqlParam.getTotalNum()) {
                	break;
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            LocalDateTime endTime=LocalDateTime.now();
            Double eclapsedTime=BigDecimal.valueOf(Duration.between(startTime, endTime).toNanos()).divide(BigDecimal.valueOf(1000000000)).doubleValue();
            BigDecimal qps= BigDecimal.valueOf(sqlParam.getTotalNum()).divide(new BigDecimal(eclapsedTime),RoundingMode.HALF_UP);
            System.out.println("\nTotal number="+sqlParam.getTotalNum()+",concurrency="+sqlParam.getConcurrency()+",speed="+qps+"/s");
    	} catch (InterruptedException | IOException e) {
    		log.error(e.toString());
		}finally {
            System.exit(0);
        }
    }
    
    private static String tableCreation(SqlParams sqlParam) {
    	String url = String.format(DATA_SOURCE_URL, sqlParam.getHost(),sqlParam.getDatabase());
    	StringBuilder sb=new StringBuilder();
    	try (Connection conn = DriverManager.getConnection(url, sqlParam.getUser(),sqlParam.getPassword())){
    		
    		String tableName=sqlParam.getTableName();
    		SqlType sqlType=sqlParam.getSqlType();
    		StringBuilder sqlSB=new StringBuilder();
    		switch (sqlType) {
				case INSERT -> {
					sqlSB.append("create table if not exists ").append(tableName).append("(id BIGINT(19) NOT NULL AUTO_INCREMENT,");
					List<String> columnNames=new ArrayList<String>();
					for(int i=0;i<sqlParam.getColumnNum();i++) {
						char currentChar = (char) ('a' + i);
						columnNames.add(String.valueOf(currentChar));
						switch(sqlParam.getColumnType()) {
							case INT -> sqlSB.append(currentChar+" int not null");
							case BIGINT -> sqlSB.append(currentChar+" bigint not null");
							default -> sqlSB.append(currentChar+" varchar(50) not null");
						}
						sqlSB.append(",");
					}
					sqlSB.append(" PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB;");
					PreparedStatement pstmt= conn.prepareStatement(sqlSB.toString());
			        pstmt.executeUpdate();
			        sb.append("insert into ");
			        sb.append(tableName);
			        sb.append("(");
					for(int j=0;j<columnNames.size();j++) {
						sb.append(columnNames.get(j));
						if(j<columnNames.size()-1) {
							sb.append(",");
						}
					}
					sb.append(")values(");
					for(int j=0;j<columnNames.size();j++) {
						sb.append("\"");
						sb.append(randomString(10));
						sb.append("\"");
						if(j<columnNames.size()-1) {
							sb.append(",");
						}
					}
					sb.append(");");
					return sb.toString();
				}
				default->{
					return "";
				}
    		}
    	} catch (SQLException e) {
        	log.error(e.toString()); 
        	return sb.toString();
        }
    }
    
    public static synchronized String randomString(int length) {
		StringBuilder buffer = new StringBuilder("0123456789abcdefjhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
    
    public static void main(String[] args) {
    	InputParams input=new InputParams.Builder().file("c:/download/test.txt").build();
    	DATA_SOURCE_URL="jdbc:mysql://%s:3306/%s?useSSL=true&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    	insertSqlFile(input);
    	System.out.println(10000%1000);
    }
}
