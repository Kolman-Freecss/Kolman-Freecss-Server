package com.kolmanfreecss.kolmanfreecss.datagithub.domain.repository.jdbc;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.UUID;

/**
 * JDBC Implementation
 * Repository for User
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mono<Optional<User>> findUserById(String userId) {
        return Mono.defer(() ->
                        Mono.fromCallable(() ->
                                jdbcTemplate.query("SELECT * FROM app_user WHERE id = ?",
                                        ps -> ps.setString(1, userId),
                                        (rs, rowNum) -> new User(
                                                UUID.fromString(rs.getString("id")),
                                                rs.getString("name"),
                                                rs.getString("email"))
                                ).stream().findFirst()
                        )
                )
                .subscribeOn(Schedulers.boundedElastic());  // Execute the operation in a scheduler suitable for blocking tasks.
    }

    public Mono<Optional<Integer>> saveUser(String name, String email) {
        return Mono.fromCallable(() ->
                Optional.of(jdbcTemplate.update("INSERT INTO app_user (name, email) VALUES (?, ?)", name, email))
        ).subscribeOn(Schedulers.boundedElastic());
    }
}
