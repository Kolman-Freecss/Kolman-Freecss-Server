package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.jobs

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.jobs.UserCleanupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * @version 1.0
 * @author Kolman-Freecss
 */
@JdbcTest
// Used to test components that interact with the database
@ContextConfiguration(classes = [UserCleanupServiceImpl])
// Load the context with the service
@Transactional
// To roll back database operations after each test
class UserCleanupServiceImplIntegrationTest extends Specification {

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    UserCleanupService userCleanupService

    def setup() {
        // Insert some users into the database for the test using H2-compatible syntax
        jdbcTemplate.execute("INSERT INTO app_user (name, email, last_login) VALUES ('activeUser', 'active@example.com', NOW())")
        jdbcTemplate.execute("INSERT INTO app_user (name, email, last_login) VALUES ('inactiveUser', 'inactive@example.com', DATEADD('YEAR', -2, NOW()))")
        jdbcTemplate.execute("INSERT INTO app_user (name, email, last_login) VALUES ('inactiveUser2', 'inactive2@example.com', DATEADD('YEAR', -3, NOW()))")
    }

    def "test cleanupInactiveUsers should delete inactive users"() {
        when: "The cleanup service is called"
        userCleanupService.cleanupInactiveUsers()

        then: "Inactive users are deleted and active users remain"
        def activeUsersCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM app_user WHERE last_login >= DATEADD('YEAR', -1, NOW())", Integer)
        def inactiveUsersCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM app_user WHERE last_login < DATEADD('YEAR', -1, NOW())", Integer)

        activeUsersCount == 1
        inactiveUsersCount == 0
    }

}
