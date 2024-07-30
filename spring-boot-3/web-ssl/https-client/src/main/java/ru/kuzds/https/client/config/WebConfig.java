package ru.kuzds.https.client.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.kuzds.https.client.config.properties.DemoProperties;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class WebConfig {

    @Bean
    @ConditionalOnExpression("${demo.ssl.secure-enable}")
    SslContext secureSslContext(DemoProperties props) throws Exception {
        try (FileInputStream trustStoreFileInputStream = new FileInputStream(ResourceUtils.getFile(props.getSsl().getPathToTrustStore()));
//             FileInputStream keyStoreFileInputStream = new FileInputStream(ResourceUtils.getFile(props.getSsl().getPathToKeyStore()))
        ) {

            KeyStore trustStore = KeyStore.getInstance("jks");
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(trustStoreFileInputStream, props.getSsl().getTrustStorePassword().toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);

//            KeyStore keyStore = KeyStore.getInstance("pkcs12");
//            keyStore.load(keyStoreFileInputStream, props.getSsl().getKeyStorePassword().toCharArray());
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
//            keyManagerFactory.init(keyStore, clientSslKeyStorePassword.toCharArray());

            return SslContextBuilder.forClient()
//                    .keyManager(keyManagerFactory)
                    .trustManager(trustManagerFactory)
                    .build();

        }
    }

    @Bean
    @ConditionalOnExpression("!${demo.ssl.secure-enable}")
    SslContext insecureSslContext() throws SSLException {
        return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    }

    @Bean
    ReactorClientHttpConnector reactorClientHttpConnector(SslContext sslContext) {
        HttpClient httpClient = HttpClient.create()
                .secure(t -> t.sslContext(sslContext))
//                .wiretap(this.getClass().getCanonicalName(), LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)
                ;

        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    WebClient webClient(@Value("${demo.url}") String url, ReactorClientHttpConnector reactorClientHttpConnector) {

        return WebClient.builder()
                .clientConnector(reactorClientHttpConnector)
                .baseUrl(url).build();
    }
}
