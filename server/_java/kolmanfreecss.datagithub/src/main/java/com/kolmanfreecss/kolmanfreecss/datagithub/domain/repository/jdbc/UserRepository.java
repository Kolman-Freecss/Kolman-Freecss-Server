package com.kolmanfreecss.kolmanfreecss.datagithub.domain.repository.jdbc;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * JDBC Implementation
 * Repository for User
 */
@Repository
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public Mono<Optional<User>> findUserById(String userId) {
        return Mono.just(jdbcTemplate.query("SELECT * FROM app_user WHERE id = ?", new Object[]{userId},
                (rs, rowNum) -> Optional.ofNullable(new User(rs.getLong("id"), 
                        rs.getString("name"), 
                        rs.getString("email")))
        ).stream().findFirst().orElse(Optional.empty()));
    }
    
    public void saveUser(String name, String email) {
        jdbcTemplate.update("INSERT INTO app_user (name, email) VALUES (?, ?)", name, email);
    }
}
