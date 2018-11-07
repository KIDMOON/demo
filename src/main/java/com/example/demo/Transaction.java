package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

/**
 * Class description goes here.
 *
 * @author Kid
 * @version 1.0
 * @date 2018/9/17
 */
@Configuration
@EnableTransactionManagement
public class Transaction {


//    @Resource(name="transactionManager")
//    private PlatformTransactionManager txManager;
//
//
//    // 创建事务管理器1
//    @Bean(name = "txManager1")
//    @Primary
//    public PlatformTransactionManager txManager1(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    // 创建事务管理器2
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
//        return new JpaTransactionManager(factory);
//    }
//
//
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return txManager;
//    }
}
