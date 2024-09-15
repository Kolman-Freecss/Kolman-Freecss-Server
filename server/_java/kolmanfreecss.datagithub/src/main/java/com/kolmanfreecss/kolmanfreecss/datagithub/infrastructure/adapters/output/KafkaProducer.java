package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC = "kolman_githubdata_channel";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Mono<Void> sendFlightEvent(final KafkaMessage event) {
        try {
            final String jsonEvent = new ObjectMapper().writeValueAsString(event);
            log.info("Producer produced the message {}", jsonEvent);
            return Mono.fromFuture(() -> kafkaTemplate.send(TOPIC, jsonEvent).whenComplete((recordMetadata, exception) -> {
                if (exception != null) {
                    log.error("Error while producing the message", exception);
                } else {
                    log.info("Message produced successfully");
                }
            })).then();
        } catch (JsonProcessingException e) {
            log.error("Error while producing the message", e);
            return Mono.error(e);
        }
        // write your handlers and post-processing logic, based on your use case
    }

}