package com.example.todoSchedulerProject.controller;

import com.example.todoSchedulerProject.service.TodoScheduleService;
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
}


