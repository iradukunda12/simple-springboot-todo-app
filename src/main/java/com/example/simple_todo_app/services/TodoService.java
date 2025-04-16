package com.example.simple_todo_app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.simple_todo_app.models.Todo;
import com.example.simple_todo_app.models.TodoDTO;
import com.example.simple_todo_app.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Convert DTO to Entity
    private Todo convertToEntity(TodoDTO dto) {
        Todo todo = new Todo();
        todo.setId(dto.getId()); // Useful when updating
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(dto.isCompleted());

        // Set createdAt only if null (i.e., new todo)
        if (dto.getId() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }
        return todo;
    }

    // Convert Entity to DTO
    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        return dto;
    }

    public TodoDTO createTodo(TodoDTO dto) {
        Todo saved = todoRepository.save(convertToEntity(dto));
        return convertToDTO(saved);
    }

    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TodoDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with ID: " + id));
        return convertToDTO(todo);
    }

    public TodoDTO updateTodo(Long id, TodoDTO dto) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with ID: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(dto.isCompleted());

        Todo updated = todoRepository.save(existing);
        return convertToDTO(updated);
    }
    
    public TodoDTO markTodoAsCompleted(Long id, boolean completed) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setCompleted(completed);
        todoRepository.save(todo);
        return convertToDTO(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

}
