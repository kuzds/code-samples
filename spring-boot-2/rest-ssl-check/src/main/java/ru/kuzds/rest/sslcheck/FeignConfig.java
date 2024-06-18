package ru.kuzds.rest.sslcheck;

import feign.Client;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class FeignConfig {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @Bean
    public Client feignClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (chain, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

        return new Client.Proxied(sslContext.getSocketFactory(), null,
            new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort))
        );
    }
}
