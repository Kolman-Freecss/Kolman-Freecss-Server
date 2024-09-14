package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC = "kolman_githubdata_channel";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendFlightEvent(final KafkaMessage event) {
        try {
            final String jsonEvent = new ObjectMapper().writeValueAsString(event);
            kafkaTemplate.send(TOPIC, event.getId(), jsonEvent);
            log.info("Producer produced the message {}", jsonEvent);
        } catch (JsonProcessingException e) {
            log.error("Error while producing the message", e);
        }
        // write your handlers and post-processing logic, based on your use case
    }

}