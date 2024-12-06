package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    // 속성

    // 생성자

    // 기능
    TodoResponseDto createTodoService(TodoRequestDto todoRequestDto);

    List<TodoResponseDto> searchAllTodosService();

    TodoResponseDto searchTodoByIdService(Long id);
}
