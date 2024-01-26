package ru.kuzds.data.jdbc.time;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {
}
