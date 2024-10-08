package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.kafka;

import com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.kafka.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "kolman_monitorization_channel", groupId = "java-kafka-consumer")
    public void flightEventConsumer(final KafkaMessage message) {
        log.info("Consumer consume Kafka message -> {}", message);

        // write your handlers and post-processing logic, based on your use case
    }

}