package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository{

    // 속성
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 기능
    @Override
    public TodoResponseDto createTodo(Todo todo) {

        // INSERT Query를 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("content", todo.getContent());
        parameters.put("writer", todo.getWriter());
        parameters.put("password", todo.getWriter());
        parameters.put("created_date", todo.getCreated_date());
        parameters.put("updated_date", todo.getUpdated_date());

        // 저장 후 생성된 key 값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(key.longValue(), todo.getTitle(), todo.getContent(), todo.getWriter(), todo.getCreated_date(), todo.getUpdated_date());
    }

    @Override
    public List<TodoResponseDto> searchAllTodos() {
        return List.of();
    }

    @Override
    public Todo searchTodoById(Long id) {
        return null;
    }

    @Override
    public void deleteTodo(Long id) {

    }
}
