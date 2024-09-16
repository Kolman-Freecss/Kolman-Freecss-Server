package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.rest;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.UserService;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(final UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/")
    public Mono<List<String>> findAllUsers() {
        try {
            return userService.findAllUsers()
                    .map(userDtos -> userDtos.stream()
                            .map(userDto -> "User found: " + userDto.name())
                            .toList())
                    .defaultIfEmpty(List.of("No users found"));
        } catch (Exception e) {
            log.error("Error finding users", e);
            return Mono.just(List.of("Error finding users"));
        }
    }
    
    @PostMapping("/")
    public Mono<String> saveUser(final @RequestBody UserDto userDto) {
        try {
            return userService.saveUser(userDto)
                    .thenReturn("User saved successfully")
                    .onErrorResume(e -> Mono.just("Error saving user"));
        } catch (Exception e) {
            log.error("Error saving user", e);
            return Mono.just("Error saving user");
        }
    }
    
    @GetMapping("/user/{userId}")
    public Mono<String> findUserById(final @PathVariable String userId) {
        try {
            return userService.findUserById(userId)
                    .map(userDto -> "User found: " + userDto.name())
                    .defaultIfEmpty("User not found");
        } catch (Exception e) {
            log.error("Error finding user", e);
            return Mono.just("Error finding user");
        }
    }
}
