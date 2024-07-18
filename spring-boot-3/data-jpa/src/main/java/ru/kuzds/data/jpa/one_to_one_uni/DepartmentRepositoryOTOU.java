package ru.kuzds.data.jpa.one_to_one_uni;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryOTOU extends JpaRepository<DepartmentOTOU, Long> {

    @Override
    @Nonnull
    List<DepartmentOTOU> findAll();

    @Override
    @Nonnull
    Optional<DepartmentOTOU> findById(@Nonnull Long id);
}
