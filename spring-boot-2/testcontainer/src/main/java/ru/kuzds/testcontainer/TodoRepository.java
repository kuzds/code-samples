package ru.kuzds.testcontainer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Query("select t from Todo t where t.completed = false")
    Iterable<Todo> getPendingTodos();
}
