package com.kolmanfreecss.kolmanfreecss.datagithub.application.service;

import com.kolmanfreecss.kolmanfreecss.datagithub.domain.dto.UserDto;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.repository.hibernate.UserHibernateRepository;
import com.kolmanfreecss.kolmanfreecss.datagithub.domain.repository.jdbc.UserRepository;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.KafkaProducer;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
@Service
public class UserService {

    private final KafkaProducer kafkaProducer;

    private final UserRepository jdbcUserRepository;

    private final UserHibernateRepository hibernateUserRepository;

    public UserService(final KafkaProducer kafkaProducer,
                       final UserRepository userRepository,
                       final UserHibernateRepository hibernateUserRepository) {
        this.kafkaProducer = kafkaProducer;
        this.jdbcUserRepository = userRepository;
        this.hibernateUserRepository = hibernateUserRepository;
    }
    
    /**
     * Find all users
     *
     * @return List of users
     */
    @Cacheable(value = "user", key = "'all'")
    public Mono<List<UserDto>> findAllUsers() {
        return Mono.just(this.hibernateUserRepository.findAll().
                stream()
                .map(user -> {
                    final KafkaMessage kafkaMessage = new KafkaMessage(UUID.randomUUID().toString(), "User created " + user.getEmail() + " " + user.getName());
                    this.kafkaProducer.sendFlightEvent(kafkaMessage).then().subscribe(); // Do nothing but no blocking.
                    return new UserDto(user.getName(), user.getEmail());
                })
                .toList());
    }
    
    /**
     * Save user
     */
    public Mono<Void> saveUser(final UserDto userDto) {
        return Mono.fromRunnable(() -> this.jdbcUserRepository.saveUser(userDto.getName(), userDto.getEmail())
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(integer -> {
                    // Send message to Kafka if user is created
                    final KafkaMessage kafkaMessage;
                    if (integer.orElse(0) > 0) {
                        kafkaMessage = new KafkaMessage(UUID.randomUUID().toString(), "User created " + userDto.getEmail() + " " + userDto.getName());
                    } else {
                        kafkaMessage = new KafkaMessage(UUID.randomUUID().toString(), "User not created " + userDto.getEmail() + " " + userDto.getName());
                    }
                    this.kafkaProducer.sendFlightEvent(kafkaMessage).subscribe(); // Do nothing but no blocking.
                }).subscribe());
    }

    /**
     * Find user by id
     *
     * @param userId User id
     * @return User
     */
    @Cacheable(value = "user", key = "#userId")
    public Mono<UserDto> findUserById(final String userId) {
        return this.jdbcUserRepository.findUserById(userId)
                .flatMap(optionalUser -> optionalUser
                        .map(user -> Mono.just(new UserDto(user.getName(), user.getEmail()))
                                .doOnNext(
                                        userDto -> {
                                            final KafkaMessage kafkaMessage = new KafkaMessage(UUID.randomUUID().toString(), "User found " + userDto.getEmail() + " " + userDto.getName());
                                            this.kafkaProducer.sendFlightEvent(kafkaMessage).subscribe(); // Do nothing but no blocking.
                                        }
                                )
                        )
                        .orElseGet(Mono::empty));
    }
}
