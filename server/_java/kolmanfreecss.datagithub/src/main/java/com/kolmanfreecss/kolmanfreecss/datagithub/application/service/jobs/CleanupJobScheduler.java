package com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Component
public class CleanupJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job cleanInactiveUsersJob;

    public CleanupJobScheduler(JobLauncher jobLauncher, 
                               Job cleanInactiveUsersJob) {
        this.jobLauncher = jobLauncher;
        this.cleanInactiveUsersJob = cleanInactiveUsersJob;
    }

    @Scheduled(cron = "0 0 0 * * ?") // every day at midnight
    public void runJob() throws Exception {
        jobLauncher.run(cleanInactiveUsersJob, new JobParameters());
    }
}
