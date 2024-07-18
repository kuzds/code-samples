package ru.kuzds.data.jpa.one_to_many_uni;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryOTMU extends JpaRepository<DepartmentOTMU, Long> {

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    List<DepartmentOTMU> findAll();

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    Optional<DepartmentOTMU> findById(@Nonnull Long id);
}
