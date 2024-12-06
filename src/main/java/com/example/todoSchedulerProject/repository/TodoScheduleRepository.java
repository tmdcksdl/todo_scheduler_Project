package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TodoScheduleRepository implements TodoRepository{

    // 속성
    private final Map<Long, Todo> todoList = new HashMap<>();

    // 생성자


    // 기능
}
