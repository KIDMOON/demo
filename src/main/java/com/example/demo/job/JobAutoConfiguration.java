package com.example.demo.job;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author kid
 */
@Configuration
public class JobAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    //配置JobFactory
    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(JobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quart.properties"));
        //不覆盖db，
        schedulerFactoryBean.setOverwriteExistingJobs(false);
        return schedulerFactoryBean;
    }

}
