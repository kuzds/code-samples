package ru.kuzds.reactor.rabbitmq.demo.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

@Configuration
public class RabbitConfig {
    @Bean
    ConnectionFactory connectionFactory(@Value("${rmq.uri}") String uri) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(uri);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.useNio();
        connectionFactory.setConnectionTimeout(0);
        return connectionFactory;
    }

    @Bean
    public SenderOptions senderOptions(ConnectionFactory connectionFactory,
                                       @Value("${rmq.connection-name}") String connectionName) {
        return new SenderOptions()
                .connectionFactory(connectionFactory)
                .connectionSupplier(factory -> factory.newConnection(connectionName + "-sender"))
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public ReceiverOptions receiverOptions(ConnectionFactory connectionFactory,
                                           @Value("${rmq.connection-name}") String connectionName) {
        return new ReceiverOptions()
                .connectionFactory(connectionFactory)
                .connectionSupplier(factory -> factory.newConnection(connectionName + "-receiver"))
                .connectionSubscriptionScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions options) {
        return RabbitFlux.createSender(options);
    }

    @Bean
    public Receiver receiver(ReceiverOptions options) {
        return RabbitFlux.createReceiver(options);
    }
}
