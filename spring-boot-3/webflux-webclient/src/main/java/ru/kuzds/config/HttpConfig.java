package ru.kuzds.config;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.net.ssl.SSLException;

@Configuration
public class HttpConfig {

    @Bean
    ReactorClientHttpConnector reactorClientHttpConnector() throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient =
                HttpClient.create()
//                        .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
//                                .host("ws-wcg")
//                                .port(18080))
                        .secure(t -> t.sslContext(sslContext))
                        .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);


        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    WebClient.Builder webClientBuilder(ReactorClientHttpConnector reactorClientHttpConnector) {
        return WebClient.builder()
                .clientConnector(reactorClientHttpConnector);
    }
}
