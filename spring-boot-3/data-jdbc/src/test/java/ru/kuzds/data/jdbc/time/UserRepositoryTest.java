package ru.kuzds.data.jdbc.time;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {
    @Container
    static PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @AfterAll
    public static void destroy() {
        POSTGRESQL.close();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL::getPassword);
        registry.add("spring.liquibase.change-log", () -> "classpath:changelog/db.changelog-test.yaml");
        registry.add("spring.liquibase.enabled", () -> "true");
    }

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(User.builder()
                        .id(null)
                        .bro(true)
                        .birthDate(OffsetDateTime.now())
                        .birthDateTime(OffsetDateTime.now())
                .build());
    }

    @Test
    void shouldGetPendingTodos() {
        List<User> users = repository.findAll();
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).hasNoNullFieldsOrProperties();
    }
}
