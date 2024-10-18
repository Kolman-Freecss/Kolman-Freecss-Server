package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.jobs;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs.UserCleanupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Slf4j
@Component
public class UserCleanupServiceImpl implements UserCleanupService {

    private final JdbcTemplate jdbcTemplate;

    public UserCleanupServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void cleanupInactiveUsers() {
        final String sql = "DELETE FROM app_user WHERE last_login < DATEADD('YEAR', -1, NOW())";
        final int rowsAffected = jdbcTemplate.update(sql);
        log.info("Deleted {} inactive users", rowsAffected);
    }

}
