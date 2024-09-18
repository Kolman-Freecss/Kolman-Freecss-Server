package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.github;

import com.kolmanfreecss.kolmanfreecss.datagithub.application.service.github.GithubDataService;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.model.GithubData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Github Data Service Implementation
 *
 * @version 1.0
 * @uthor Kolman-Freecss
 */
@Slf4j
@Component
public class GithubDataServiceImpl implements GithubDataService {
    
    final WebClient webClient;
    
    public GithubDataServiceImpl(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com")
                .defaultHeader("Accept", "Bearer " + System.getenv("GITHUB_TOKEN"))
                .build();
    }

    @Override
    public Mono<GithubData> getBasicInfo() {
        return webClient.get()
                .uri("/repos/Kolman-Freecss/kolmanfreecss")
                .retrieve()
                .bodyToMono(GithubData.class)
                .doOnError(e -> log.error("Error getting basic info", e));
    }

}
