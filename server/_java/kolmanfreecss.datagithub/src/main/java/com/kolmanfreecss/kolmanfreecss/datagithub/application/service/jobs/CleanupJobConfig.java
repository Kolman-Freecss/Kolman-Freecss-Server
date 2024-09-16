package com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Configuration
@EnableBatchProcessing
public class CleanupJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CleanupTasklet cleanInactiveUsersTasklet;

    public CleanupJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CleanupTasklet cleanInactiveUsersTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.cleanInactiveUsersTasklet = cleanInactiveUsersTasklet;
    }

    @Bean
    public Job cleanInactiveUsersJob() {
        return jobBuilderFactory.get("cleanInactiveUsersJob")
            .start(cleanInactiveUsersStep())
            .build();
    }

    @Bean
    public Step cleanInactiveUsersStep() {
        return stepBuilderFactory.get("cleanInactiveUsersStep")
            .tasklet(cleanInactiveUsersTasklet)
            .build();
    }
}