package com.example.simple_todo_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simple_todo_app.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    

}
