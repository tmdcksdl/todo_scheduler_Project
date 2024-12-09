package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoScheduleService implements TodoService{

    // 속성
    private final TodoRepository todoRepository;

    // 생성자
    public TodoScheduleService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 기능
    // ::: 일정 생성 서비스
    @Override
    public TodoResponseDto createTodoService(TodoRequestDto todoRequestDto) {

        // 요청 받은 데이터로 일정 객체 생성
        Todo todo = new Todo(todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword());

        // 저장
        return todoRepository.createTodo(todo);
    }

    // ::: 전체 일정 조회 서비스
    @Override
    public List<TodoResponseDto> searchAllTodosService(String updated_date, String writer) {

        // 리스트 형태의 TodoResponseDto를 바로 반환받는다.
        List<TodoResponseDto> allTodos = todoRepository.searchAllTodos(updated_date, writer);

        return allTodos;
    }

    // ::: 선택 일정 조회 서비스
    @Override
    public TodoResponseDto searchTodoByIdService(Long id) {

        Optional<Todo> optionalTodo = todoRepository.searchTodoById(id);

        if (optionalTodo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }

        return new TodoResponseDto(optionalTodo.get());
    }

    // ::: 선택 일정 수정 서비스
    @Transactional
    @Override
    public TodoResponseDto updateTodoService(Long id, String title, String content, String writer, String password) {

        // 필수값 검증
        if (title == null || content == null || writer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목, 내용, 작성자명이 포함되어 있지 않습니다.");
        }

        int updateRow = todoRepository.updateTodo(id, title, content, writer, password);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }

        return new TodoResponseDto(todoRepository.searchTodoById(id).get());
    }

    @Override
    public void deleteTodoService(Long id, String password) {

//        Todo todo = todoRepository.searchTodoById(id);
//
//        if (todo == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
//        }
//
//        if (password.equals(todo.getPassword())) {
//            todoRepository.deleteTodo(id);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
//        }
    }
}
