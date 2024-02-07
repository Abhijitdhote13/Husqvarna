package com.husqvarna.todo.repository;

import com.husqvarna.todo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByCompletedFalse();

    List<TodoItem> findByCompletedTrue();
}
