package ru.kuzds.testcontainer.activemq.config;

import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@ConfigurationProperties(prefix = "kuzds.activemq")
@Setter
public class ActivemqConfig {

    private String brokerUrl;
    private String user;
    private String password;


    @Bean
    public RedeliveryPolicy cftRedeliveryPolicy() {
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setMaximumRedeliveries(2);
        policy.setInitialRedeliveryDelay(2000);
        policy.setRedeliveryDelay(2000);
        return policy;
    }

    @Bean
    public ConnectionFactory todoConnectionFactory(RedeliveryPolicy cftRedeliveryPolicy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setWatchTopicAdvisories(false);
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(user);
        factory.setPassword(password);
        factory.setTrustAllPackages(true);
        factory.setRedeliveryPolicy(cftRedeliveryPolicy);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory todoListenerContainerFactory(ConnectionFactory todoConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(todoConnectionFactory);
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    public JmsTemplate todoJmsTemplate(final ConnectionFactory todoConnectionFactory) {
        return new JmsTemplate(todoConnectionFactory);
    }

}
