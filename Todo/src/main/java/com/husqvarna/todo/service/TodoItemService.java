package com.husqvarna.todo.service;

import com.husqvarna.todo.util.ResourceNotFoundException;
import com.husqvarna.todo.model.TodoItem;
import com.husqvarna.todo.repository.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {

    private static final Logger logger = LoggerFactory.getLogger(TodoItemService.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem> getAll() {
        return todoItemRepository.findAll();
    }

    public List<TodoItem> getNotCompletedTodos() {
        return todoItemRepository.findByCompletedFalse();
    }

    public List<TodoItem> getCompletedTodos() {
        return todoItemRepository.findByCompletedTrue();
    }

    public Optional<TodoItem> getById(Long id) {
        return todoItemRepository.findById(id);
    }

    public TodoItem updateTodo(Long id, TodoItem updatedTodo) {
        TodoItem existingTodo = todoItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo item not found with ID: " + id));

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setCompleted(updatedTodo.getCompleted());

        return todoItemRepository.save(existingTodo);
    }

    public TodoItem save(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    public void delete(Long id) {
        if (todoItemRepository.existsById(id)) {
            todoItemRepository.deleteById(id);
        } else {
            logger.warn("Attempted to delete non-existing todo item with ID: {}", id);
            throw new ResourceNotFoundException("Todo item not found with ID: " + id);

        }
    }

    public void deleteAll() {
        todoItemRepository.deleteAll();
    }
}