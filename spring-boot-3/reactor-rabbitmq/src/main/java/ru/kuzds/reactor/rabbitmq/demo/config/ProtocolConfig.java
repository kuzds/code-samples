package ru.kuzds.reactor.rabbitmq.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;
import ru.kuzds.reactor.rabbitmq.protocol.helper.ProtocolHelper;

@Configuration
public class ProtocolConfig {

    @Bean
    ProtocolHelper protocolHelper(ObjectMapper objectMapper, Sender sender, Receiver receiver) {
        return new ProtocolHelper(objectMapper, sender, receiver);
    }
}
