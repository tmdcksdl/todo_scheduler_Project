package com.example.todoSchedulerProject.controller;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.service.TodoScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // ::: 전체 일정 조회 API
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> searchAllTodosAPI(){

        return new ResponseEntity<>(todoScheduleService.searchAllTodosService(), HttpStatus.OK);
    }

    // ::: 선택 일정 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> searchTodoByIdAPI(@PathVariable Long id) {


        return new ResponseEntity<>(todoScheduleService.searchTodoByIdService(id), HttpStatus.OK);
    }

    // ::: 선택 일정 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoAPI(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
       return new ResponseEntity<>(todoScheduleService.updateTodoService(id, todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword(), todoRequestDto.getUpdated_date()), HttpStatus.OK);
    }
}


