package com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version 1.0
 * @author uthor Kolman-Freecss
 */
@Service
public class CleanupTasklet implements Tasklet {
    
    private final UserCleanupService userCleanupService;
    
    public CleanupTasklet(final UserCleanupService userCleanupService) {
        this.userCleanupService = userCleanupService;
    }

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        userCleanupService.cleanupInactiveUsers();
        return RepeatStatus.FINISHED;
    }
}
