package com.husqvarna.todo.controller;

import com.husqvarna.todo.model.TodoItem;
import com.husqvarna.todo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoContoller {
    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TodoItem>> getAll() {
        try {
            List<TodoItem> todos = todoItemService.getAll();
            if (todos.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(todos);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<TodoItem>> getNotCompletedTodos() {
        try {
            List<TodoItem> notCompletedTodos = todoItemService.getNotCompletedTodos();
            return ResponseEntity.ok(notCompletedTodos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoItem>> getCompletedTodos() {
        try {
            List<TodoItem> completedTodos = todoItemService.getCompletedTodos();
            return ResponseEntity.ok(completedTodos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todo) {
        try {
            TodoItem createdTodo = todoItemService.save(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodo(@PathVariable Long id, @RequestBody TodoItem updatedTodo) {
        try {
            TodoItem todo = todoItemService.updateTodo(id, updatedTodo);
            if (todo != null) {
                return ResponseEntity.ok(todo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoItemService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        try {
            todoItemService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
