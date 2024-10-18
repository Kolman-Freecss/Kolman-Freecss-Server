package com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Configuration
@EnableBatchProcessing
public class CleanupJobConfig {

    private final CleanupTasklet cleanInactiveUsersTasklet;

    public CleanupJobConfig(CleanupTasklet cleanInactiveUsersTasklet) {
        this.cleanInactiveUsersTasklet = cleanInactiveUsersTasklet;
    }

    @Bean
    public Job cleanInactiveUsersJob(final JobRepository jobRepository, final Step cleanInactiveUsersStep) {
        return new JobBuilder("cleanInactiveUsersJob", jobRepository)
                .start(cleanInactiveUsersStep)
                .build();
    }

    @Bean
    public Step cleanInactiveUsersStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("cleanInactiveUsersStep", jobRepository)
                .tasklet(cleanInactiveUsersTasklet, transactionManager)
                .build();
    }

}