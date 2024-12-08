package com.example.todoSchedulerProject.controller;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.service.TodoScheduleService;
import com.example.todoSchedulerProject.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoScheduleController {

    // 속성
    private final TodoService todoService;

    // 생성자
    public TodoScheduleController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 기능
    // ::: 일정 생성 API
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodoAPI(@RequestBody TodoRequestDto todoRequestDto) {

        return new ResponseEntity<>(todoService.createTodoService(todoRequestDto),HttpStatus.CREATED);
    }

    // ::: 전체 일정 조회 API
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> searchAllTodosAPI(
            @RequestParam(required = false) String updated_date,
            @RequestParam(required = false) String writer
    ){
        return new ResponseEntity<>(todoService.searchAllTodosService(updated_date, writer), HttpStatus.OK);
    }

    // ::: 선택 일정 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> searchTodoByIdAPI(@PathVariable Long id) {


        return new ResponseEntity<>(todoService.searchTodoByIdService(id), HttpStatus.OK);
    }

    // ::: 선택 일정에 대한 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoAPI(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
       return new ResponseEntity<>(todoService.updateTodoService(id, todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword()), HttpStatus.OK);
    }

    // ::: 선택 일정 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoAPI(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
        todoService.deleteTodoService(id, todoRequestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


