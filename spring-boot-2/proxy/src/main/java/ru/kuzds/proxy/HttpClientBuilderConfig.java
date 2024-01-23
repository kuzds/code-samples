package ru.kuzds.proxy;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientBuilderConfig {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        return HttpClientBuilder.create()
            .setProxy(new HttpHost(proxyHost, proxyPort));
    }
}
