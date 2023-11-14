package ru.kuzds.activemq.config;

import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "kuzds.activemq")
@Setter
public class JmsConfig {

    private String brokerUrl;
    private String user;
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
    public ActiveMQConnectionFactory dboAmqConnectionFactory(RedeliveryPolicy policy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setWatchTopicAdvisories(false);
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(user);
        factory.setPassword(password);
        factory.setTrustAllPackages(true);
        factory.setRedeliveryPolicy(policy);
        return factory;
    }

//    https://stackoverflow.com/questions/19560479/which-is-better-pooledconnectionfactory-or-cachingconnectionfactory
//    @Bean
//    public PooledConnectionFactory dboPool(ActiveMQConnectionFactory dboAmqConnectionFactory) {
//        PooledConnectionFactory pool = new PooledConnectionFactory();
//        pool.setMaxConnections(10);
//        pool.setConnectionFactory(dboAmqConnectionFactory);
//        return pool;
//    }
}
