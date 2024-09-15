package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.rest;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.GeneralGithubDataService;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/github")
public class GeneralGithubDataController {

    private final GeneralGithubDataService generalGithubDataService;

    public GeneralGithubDataController(GeneralGithubDataService generalGithubDataService) {
        this.generalGithubDataService = generalGithubDataService;
    }
    
    @GetMapping("/user/{userId}")
    public Mono<String> findUserById(final @PathVariable String userId) {
        try {
            return generalGithubDataService.findUserById(userId)
                    .map(userDto -> "User found: " + userDto.name())
                    .defaultIfEmpty("User not found");
        } catch (Exception e) {
            log.error("Error finding user", e);
            return Mono.just("Error finding user");
        }
    }

    @PostMapping("/send")
    public Mono<String> sendGithubData(final @RequestBody List<KafkaMessage> githubData) {
        try {
            return generalGithubDataService.sendGithubData(githubData)
                    .thenReturn("Github data sent successfully")
                    .onErrorResume(e -> Mono.just("Error sending github data"));
        } catch (Exception e) {
            log.error("Error sending github data", e);
            return Mono.just("Error sending github data");
        }
    }

}
