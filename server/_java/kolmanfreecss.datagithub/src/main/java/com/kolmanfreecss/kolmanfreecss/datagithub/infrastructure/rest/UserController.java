package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.rest;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.UserService;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    
    @Operation(summary = "Find all users", description = "Find all users")
    @ApiResponse(responseCode = "200", description = "Users found")
    @ApiResponse(responseCode = "500", description = "Error finding users")
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
    
    @Operation(summary = "Save user", description = "Save user by userDto")
    @ApiResponse(responseCode = "200", description = "User saved successfully")
    @ApiResponse(responseCode = "500", description = "Error saving user")
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
    
    @Operation(summary = "Find user by id", description = "Find user by the given userId")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "500", description = "Error finding user")
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
