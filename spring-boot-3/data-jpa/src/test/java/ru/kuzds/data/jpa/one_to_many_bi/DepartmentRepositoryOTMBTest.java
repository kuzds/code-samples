package ru.kuzds.data.jpa.one_to_many_bi;

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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class DepartmentRepositoryOTMBTest {
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
    DepartmentRepositoryOTMB repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        DepartmentOTMB department = new DepartmentOTMB();
        department.setName("department");

        List<EmployeeOTMB> employees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EmployeeOTMB employee = new EmployeeOTMB();
            employee.setName("employee" + i);
            employee.setDepartment(department);
            employees.add(employee);
        }
        department.setEmployees(employees);
        repository.save(department);
    }

    @Test
    void test() {
        List<DepartmentOTMB> departments = repository.findAll();
        assertThat(departments).hasSize(1);
    }
}