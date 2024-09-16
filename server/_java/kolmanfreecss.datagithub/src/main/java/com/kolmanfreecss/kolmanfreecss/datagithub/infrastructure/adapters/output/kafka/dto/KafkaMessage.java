package com.kolmanfreecss.kolmanfreecss.datagithub.infrastructure.adapters.output.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(of = {"id", "message"})
public class KafkaMessage {
    
    private final String id;
    private final String message;
    
}
