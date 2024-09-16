package com.kolmanfreecss.kolmanfreecss.datagithub.application.service;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.GithubDataDto;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.KafkaProducer;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * General Github Data Service
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Service
public class GeneralGithubDataService {

    private final KafkaProducer kafkaProducer;

    public GeneralGithubDataService(final KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Send github data
     *
     * @param githubData List of github data
     * @return Mono
     */
    public Mono<Void> sendGithubData(final List<GithubDataDto> githubData) {
        return Flux.fromIterable(githubData)
                .flatMap(data -> {
                    KafkaMessage message = new KafkaMessage(UUID.randomUUID().toString(),
                            "github_data " + data.title() + " " + data.description() + " " + data.url() + " " + data.email().orElse(""));
                    return kafkaProducer.sendFlightEvent(message);
                })
                .then();
    }

}
