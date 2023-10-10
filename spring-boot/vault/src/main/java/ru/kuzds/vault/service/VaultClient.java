package ru.kuzds.vault.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaultClient {

    @Value("${vault.path}")
    private String vaultPath;

    private final VaultTemplate vaultTemplate;

    @PostConstruct
    void init() {
        Map<String, String> secretsMap = (Map<String, String>) vaultTemplate.read(vaultPath).getData().get("data");
        log.info(secretsMap.toString());
    }
}
