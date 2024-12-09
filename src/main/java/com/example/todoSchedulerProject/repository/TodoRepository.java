package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoResponseDto;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    // 속성

    // 생성자

    // 기능
    TodoResponseDto createTodo(Todo todo);

    List<TodoResponseDto> searchAllTodos(String updated_date, String writer);

    Optional<Todo> searchTodoById(Long id);

    int updateTodo(Long id, String title, String content, String writer, String password);

    int deleteTodo(Long id, String password);
}
