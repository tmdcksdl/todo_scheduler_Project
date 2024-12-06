package com.example.todoSchedulerProject.controller;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.service.TodoScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoScheduleController {

    // 속성
    private final TodoScheduleService todoScheduleService;

    // 생성자
    public TodoScheduleController(TodoScheduleService todoScheduleService) {
        this.todoScheduleService = todoScheduleService;
    }

    // 기능
    // ::: 일정 생성 API
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodoAPI(@RequestBody TodoRequestDto todoRequestDto) {

        return new ResponseEntity<>(todoScheduleService.createTodoService(todoRequestDto),HttpStatus.CREATED);
    }
}


