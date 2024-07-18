package ru.kuzds.data.jpa.one_to_many_bi;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryOTMB extends JpaRepository<DepartmentOTMB, Long> {

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    List<DepartmentOTMB> findAll();

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    Optional<DepartmentOTMB> findById(@Nonnull Long id);
}
