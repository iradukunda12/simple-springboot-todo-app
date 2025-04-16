package com.example.simple_todo_app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO dto) {
        return ResponseEntity.ok(todoService.createTodo(dto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }
    
     @PutMapping("/{id}")
     public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody TodoDTO dto) {
         return ResponseEntity.ok(todoService.updateTodo(id, dto));
     }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDTO> markCompleted(
            @PathVariable Long id,
            @RequestParam boolean completed
    ) {
        return ResponseEntity.ok(todoService.markTodoAsCompleted(id, completed));
    }




}
