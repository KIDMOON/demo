package com.example.demo.job;

import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @author kid
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Component
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("javavajav");

    }
}
