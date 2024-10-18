package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.rest;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.GeneralGithubDataService;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.GithubDataDto;
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
@RequestMapping("/api/v1/github")
public class GeneralGithubDataController {

    private final GeneralGithubDataService generalGithubDataService;

    public GeneralGithubDataController(GeneralGithubDataService generalGithubDataService) {
        this.generalGithubDataService = generalGithubDataService;
    }
    
    @Operation(summary = "Send github data", description = "Send github data")
    @ApiResponse(responseCode = "200", description = "Github data sent successfully")
    @ApiResponse(responseCode = "500", description = "Error sending github data")
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
    
    @Operation(summary = "Get basic info", description = "Get basic info")
    @ApiResponse(responseCode = "200", description = "Basic info found")
    @ApiResponse(responseCode = "500", description = "Error getting basic info")
    @GetMapping("/basic-info")
    public Mono<String> getBasicInfo() {
        try {
            return generalGithubDataService.getBasicInfo()
                    .onErrorResume(e -> Mono.just("Error getting basic info"));
        } catch (Exception e) {
            log.error("Error getting basic info", e);
            return Mono.just("Error getting basic info");
        }
    }

}
