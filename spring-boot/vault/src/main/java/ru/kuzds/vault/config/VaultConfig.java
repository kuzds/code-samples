package ru.kuzds.vault.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class VaultConfig extends AbstractVaultConfiguration {

    public static final String LOGIN_PATH = "/v1/auth/userpass/login/";

    private final ClientHttpRequestFactory requestFactory;
    private final RestTemplate restTemplate;

    @Value("${vault.url}")
    private String vaultUrl;

    @Value("${vault.username}")
    private String username;

    @Value("${vault.password}")
    private String password;

    @Override
    public VaultEndpoint vaultEndpoint() {
        try {
            return VaultEndpoint.from(new URI(vaultUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientAuthentication clientAuthentication() {
//        return new TokenAuthentication("token");

        @Data
        @AllArgsConstructor
        class Password {
            private String password;
        }

        return () -> VaultToken.of(
                ((Map<String, String>) restTemplate.postForEntity(vaultUrl + LOGIN_PATH + username, new Password(password), Map.class)
                        .getBody().get("auth")).get("client_token")
        );
    }

    @Bean
    @Override
    public ClientFactoryWrapper clientHttpRequestFactoryWrapper() {
        return new ClientFactoryWrapper(requestFactory);
    }
}
