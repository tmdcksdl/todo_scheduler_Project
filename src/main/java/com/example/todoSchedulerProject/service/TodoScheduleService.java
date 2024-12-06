package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.repository.TodoScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    @Override
    public TodoResponseDto createTodoService(TodoRequestDto todoRequestDto) {

        Todo todo = new Todo(todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword(), todoRequestDto.getCreated_date(), todoRequestDto.getUpdated_date());

        Todo createdTodo = todoScheduleRepository.createTodo(todo);

        return new TodoResponseDto(createdTodo);
    }

    // ::: 전체 일정 조회 서비스
    @Override
    public List<TodoResponseDto> searchAllTodosService() {

        List<TodoResponseDto> allTodos = todoScheduleRepository.searchAllTodos();

        return allTodos;
    }

    // ::: 선택 일정 조회 서비스
    @Override
    public TodoResponseDto searchTodoByIdService(Long id) {

        Todo todo = todoScheduleRepository.searchTodoById(id);

        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }

        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto updateTodoService(Long id, String title, String content, String writer, String password, String updated_date) {

        Todo todo = todoScheduleRepository.searchTodoById(id);

        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }

        if ( title == null || content == null || writer == null || updated_date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목, 내용, 작성자명, 수정일이 포함되어 있지 않습니다.");
        }

        if (password.equals(todo.getPassword())) {
            todo.updateTodo(title, content, writer, updated_date);
        }  else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }


        return new TodoResponseDto(todo);
    }

}
