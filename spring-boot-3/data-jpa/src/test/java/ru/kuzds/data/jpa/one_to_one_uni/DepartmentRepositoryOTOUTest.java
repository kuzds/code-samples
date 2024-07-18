package ru.kuzds.data.jpa.one_to_one_uni;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class DepartmentRepositoryOTOUTest {
    @Container
    static PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL::getPassword);
    }

    @Autowired
    DepartmentRepositoryOTOU repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        DepartmentOTOU department = new DepartmentOTOU();
        department.setName("department");

        EmployeeOTOU employee = new EmployeeOTOU();
        employee.setName("employee");

        department.setEmployee(employee);
        repository.save(department);
    }

    @Test
    void test() {
        List<DepartmentOTOU> departments = repository.findAll();
        assertThat(departments).hasSize(1);
    }
}