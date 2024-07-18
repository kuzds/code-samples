package ru.kuzds.data.jpa.many_to_many;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryMTM extends JpaRepository<DepartmentMTM, Long> {

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    List<DepartmentMTM> findAll();

    @Override
    @Nonnull
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "employees")
    Optional<DepartmentMTM> findById(@Nonnull Long id);
}
