package com.ccic;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //spring boot 2.x版本与activiti集成发生异常的处理方式
//该注解会扫描相应的包
//@ServletComponentScan
//@EnableCaching  //开启缓存
public class WorkApplication {

	public static void main(String[] args) {
			SpringApplication.run(WorkApplication.class, args);
	}
}
