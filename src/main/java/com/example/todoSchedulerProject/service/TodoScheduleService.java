package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.repository.TodoScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoScheduleService implements TodoService{

    // 속성
    private final TodoScheduleRepository todoScheduleRepository;

    // 생성자
    public TodoScheduleService(TodoScheduleRepository todoScheduleRepository) {
        this.todoScheduleRepository = todoScheduleRepository;
    }

    // 기능
}
