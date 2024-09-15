package com.kolmanfreecss.kolmanfreecss.datagithub.application.service;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.UserDto;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.repository.jdbc.UserRepository;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.KafkaProducer;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GeneralGithubDataService {

    private final KafkaProducer kafkaProducer;

    private final UserRepository userRepository;

    public GeneralGithubDataService(final KafkaProducer kafkaProducer,
                                    final UserRepository userRepository) {
        this.kafkaProducer = kafkaProducer;
        this.userRepository = userRepository;
    }

    @Cacheable(value = "user", key = "#userId")
    public Mono<UserDto> findUserById(final String userId) {
        return this.userRepository.findUserById(userId)
                .flatMap(optionalUser -> optionalUser
                        .map(user -> Mono.just(new UserDto(user.getName(), user.getEmail())))
                        .orElseGet(Mono::empty));
    }

    public Mono<Void> sendGithubData(final List<KafkaMessage> githubData) {
        userRepository.saveUser("name", "email");
        return Flux.fromIterable(githubData)
                .flatMap(kafkaProducer::sendFlightEvent)
                .then();
    }

}
