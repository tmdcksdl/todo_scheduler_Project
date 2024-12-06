package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TodoScheduleRepository implements TodoRepository{

    // 속성
    private final Map<Long, Todo> todoList = new HashMap<>();

    // 생성자


    // 기능
    // ::: 일정 생성
    @Override
    public Todo createTodo(Todo todo) {

        // todo의 id 자동 생성
        Long todoId = todoList.isEmpty() ? 1 : Collections.max(todoList.keySet()) + 1;

        todo.setId(todoId);

        todoList.put(todoId, todo);

        return todo;
    }

    // ::: 전체 일정 조회(조건 적용은 JDBC와 연결할 때 추가할 예정)
    @Override
    public List<TodoResponseDto> searchAllTodos() {

        List<TodoResponseDto> allTodos = new ArrayList<>();

        for (Todo todo : todoList.values()) {
            TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
            allTodos.add(todoResponseDto);
        }

        return allTodos;
    }

    // ::: 선택 일정 조회
    @Override
    public Todo searchTodoById(Long id) {

        return todoList.get(id);
    }

    // ::: 선택 일정 삭제
    @Override
    public void deleteTodo(Long id) {

        todoList.remove(id);
    }
}
