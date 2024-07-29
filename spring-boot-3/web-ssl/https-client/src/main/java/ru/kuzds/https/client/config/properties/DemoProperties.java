package ru.kuzds.https.client.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {

    private String url;
    private Path path;
    private Ssl ssl;

    @Data
    public static class Path {
        private String test;
    }

    @Data
    public static class Ssl {
        private boolean secureEnable;
//        private String pathToKeyStore;
//        private String keyStorePassword;
        private String pathToTrustStore;
        private String trustStorePassword;
    }
}
