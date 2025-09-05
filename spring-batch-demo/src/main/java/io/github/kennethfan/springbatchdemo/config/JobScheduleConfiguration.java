package io.github.kennethfan.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class JobScheduleConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("importUserJob")
    @Autowired
    private Job job;

    @Scheduled(cron = "0 * * * * ?") // 每天凌晨2点执行
    public void scheduleBatchJob() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
    }
}
