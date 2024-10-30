package com.brenchmark;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.fastjson2.JSON;
import com.brenchmark.common.util.MySQLBrenchMark;
import com.brenchmark.model.InputParams;

@SpringBootApplication
public class Application implements ApplicationRunner {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
       
    @Override
    public void run(ApplicationArguments args) {
    	Map<String,Object> map=args.getOptionNames().stream().collect(Collectors.toMap(a->a,a->args.getOptionValues(a).get(0)));
    	InputParams input=JSON.parseObject(JSON.toJSONString(map),InputParams.class);
        MySQLBrenchMark.insertSqlFile(input);
    }

}
