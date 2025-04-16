package com.example.simple_todo_app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.simple_todo_app.models.ApiResponse;
import com.example.simple_todo_app.models.TodoDTO;
import com.example.simple_todo_app.services.TodoService;

@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<TodoDTO>>> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return ResponseEntity.ok(new ApiResponse<>("All todos retrieved successfully", todos));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TodoDTO>> createTodo(@RequestBody TodoDTO dto) {
        TodoDTO created = todoService.createTodo(dto);
        return new ResponseEntity<>(new ApiResponse<>("Todo created successfully", created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoDTO>> getTodoById(@PathVariable Long id) {
        TodoDTO todo = todoService.getTodoById(id);
        return ResponseEntity.ok(new ApiResponse<>("Todo fetched successfully", todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoDTO>> updateTodo(@PathVariable Long id, @RequestBody TodoDTO dto) {
        TodoDTO updated = todoService.updateTodo(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("Todo updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(new ApiResponse<>("Todo deleted successfully", null));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<TodoDTO>> markCompleted(
            @PathVariable Long id,
            @RequestParam boolean completed) {
        TodoDTO updated = todoService.markTodoAsCompleted(id, completed);
        return ResponseEntity.ok(new ApiResponse<>("Todo completion status updated", updated));
    }
}
