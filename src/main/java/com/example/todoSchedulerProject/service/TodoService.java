package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {

    // 속성

    // 생성자

    // 기능
    TodoResponseDto createTodoService(TodoRequestDto todoRequestDto);

    List<TodoResponseDto> searchAllTodosService(String updated_date, String writer);

    TodoResponseDto searchTodoByIdService(Long id);

    TodoResponseDto updateTodoService(Long id, String title, String content, String writer, String password);

    void deleteTodoService(Long id, String password);
}
