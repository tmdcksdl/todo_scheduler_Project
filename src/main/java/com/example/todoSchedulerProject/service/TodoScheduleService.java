package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
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
    // ::: 일정 생성 서비스
    public TodoResponseDto createTodoService(TodoRequestDto todoRequestDto) {

        Todo todo = new Todo(todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword(), todoRequestDto.getCreated_date(), todoRequestDto.getUpdated_date());

        Todo createdTodo = todoScheduleRepository.createTodo(todo);

        return new TodoResponseDto(createdTodo);
    }
}
