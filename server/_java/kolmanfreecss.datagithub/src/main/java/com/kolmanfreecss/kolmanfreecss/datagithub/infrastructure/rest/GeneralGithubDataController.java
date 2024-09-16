package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.rest;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.GeneralGithubDataService;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.GithubDataDto;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
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
@RequestMapping("/api/v1/github")
public class GeneralGithubDataController {

    private final GeneralGithubDataService generalGithubDataService;

    public GeneralGithubDataController(GeneralGithubDataService generalGithubDataService) {
        this.generalGithubDataService = generalGithubDataService;
    }
    
    @PostMapping("/send")
    public Mono<String> sendGithubData(final @RequestBody List<GithubDataDto> githubData) {
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
