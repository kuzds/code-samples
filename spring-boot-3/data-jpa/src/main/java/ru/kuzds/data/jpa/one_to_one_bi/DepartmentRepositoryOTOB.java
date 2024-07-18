package ru.kuzds.data.jpa.one_to_one_bi;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryOTOB extends JpaRepository<DepartmentOTOB, Long> {

    @Override
    @Nonnull
    List<DepartmentOTOB> findAll();

    @Override
    @Nonnull
    Optional<DepartmentOTOB> findById(@Nonnull Long id);
}
