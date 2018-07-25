package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * @author biansuokou
 */
@ComponentScan
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@EnableTransactionManagement
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public Object test(AmqpTemplate AmqpTemplate){
//		System.out.println("SSSSSSSSS"+AmqpTemplate.getClass().getName());
//		return  new Object();
//	}

}
