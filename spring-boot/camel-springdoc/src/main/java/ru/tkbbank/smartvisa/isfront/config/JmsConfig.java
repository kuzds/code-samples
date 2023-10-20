package ru.tkbbank.smartvisa.isfront.config;

import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "activemq")
@Setter
public class JmsConfig {

    private String url;
    private String username;
    private String password;


    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setMaximumRedeliveries(2);
        policy.setInitialRedeliveryDelay(2000);
        policy.setRedeliveryDelay(2000);
        return policy;
    }

    @Bean
    public ActiveMQConnectionFactory amqConnectionFactory(RedeliveryPolicy policy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setWatchTopicAdvisories(false);
        factory.setBrokerURL(url);
        factory.setUserName(username);
        factory.setPassword(password);
        factory.setTrustAllPackages(true);
        factory.setRedeliveryPolicy(policy);
        return factory;
    }

    @Bean
    public PooledConnectionFactory pool(ActiveMQConnectionFactory amqConnectionFactory) {
        PooledConnectionFactory pool = new PooledConnectionFactory();
        pool.setMaxConnections(10);
        pool.setConnectionFactory(amqConnectionFactory);
        return pool;
    }

    @Bean
    public JmsConfiguration jmsConfiguration(PooledConnectionFactory pool) {
        JmsConfiguration conf = new JmsConfiguration();
        conf.setConnectionFactory(pool);
        conf.setMaxConcurrentConsumers(2);
        conf.setConcurrentConsumers(1);
        conf.setTransacted(true);
        conf.setLazyCreateTransactionManager(true);
        conf.setCacheLevelName("CACHE_CONSUMER");
        return conf;
    }

    @Bean
    public ActiveMQComponent activemq(JmsConfiguration jmsConfiguration) {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(jmsConfiguration);
        return component;
    }
}
