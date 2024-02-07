package com.husqvarna.todo;

import com.husqvarna.todo.model.TodoItem;
import com.husqvarna.todo.repository.TodoItemRepository;
import com.husqvarna.todo.service.TodoItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TodoApplicationTests {
	@MockBean
	private TodoItemRepository todoItemRepository;

	@Autowired
	private TodoItemService todoItemService;

	@Test
	void testGetAll() {
		when(todoItemRepository.findAll()).thenReturn(Arrays.asList(new TodoItem(), new TodoItem()));
		List<TodoItem> result = todoItemService.getAll();
		assertEquals(2, result.size());
		verify(todoItemRepository, times(1)).findAll();
	}

	@Test
	void testGetNotCompletedTodos() {
		when(todoItemRepository.findByCompletedFalse()).thenReturn(Arrays.asList(new TodoItem(), new TodoItem()));
		List<TodoItem> result = todoItemService.getNotCompletedTodos();
		assertEquals(2, result.size());
		verify(todoItemRepository, times(1)).findByCompletedFalse();
	}

	@Test
	void testGetCompletedTodos() {
		when(todoItemRepository.findByCompletedTrue()).thenReturn(Arrays.asList(new TodoItem(), new TodoItem()));
		List<TodoItem> result = todoItemService.getCompletedTodos();
		assertEquals(2, result.size());
		verify(todoItemRepository, times(1)).findByCompletedTrue();
	}

	@Test
	void testGetById() {
		when(todoItemRepository.findById(1L)).thenReturn(Optional.of(new TodoItem()));
		Optional<TodoItem> result = todoItemService.getById(1L);
		assertTrue(result.isPresent());
		verify(todoItemRepository, times(1)).findById(1L);
	}

	@Test
	void testGetByIdNotFound() {
		when(todoItemRepository.findById(1L)).thenReturn(Optional.empty());
		Optional<TodoItem> result = todoItemService.getById(1L);
		assertTrue(result.isEmpty());
		verify(todoItemRepository, times(1)).findById(1L);
	}

	@Test
	void testUpdateTodo() {
		when(todoItemRepository.findById(1L)).thenReturn(Optional.of(new TodoItem()));
		when(todoItemRepository.save(any(TodoItem.class))).thenReturn(new TodoItem());
		TodoItem updated = todoItemService.updateTodo(1L, new TodoItem());
		assertNotNull(updated);
		verify(todoItemRepository, times(1)).findById(1L);
		verify(todoItemRepository, times(1)).save(any(TodoItem.class));
	}

	@Test
	void testUpdateTodoNotFound() {
		when(todoItemRepository.findById(1L)).thenReturn(Optional.empty());
		TodoItem updated = todoItemService.updateTodo(1L, new TodoItem());
		assertNull(updated);
		verify(todoItemRepository, times(1)).findById(1L);
		verify(todoItemRepository, never()).save(any(TodoItem.class));
	}

	@Test
	void testSave() {
		when(todoItemRepository.save(any(TodoItem.class))).thenReturn(new TodoItem());
		TodoItem saved = todoItemService.save(new TodoItem());
		assertNotNull(saved);
		verify(todoItemRepository, times(1)).save(any(TodoItem.class));
	}

	@Test
	void testDelete() {
		doNothing().when(todoItemRepository).deleteById(1L);
		todoItemService.delete(1L);
		verify(todoItemRepository, times(1)).deleteById(1L);
	}
}
