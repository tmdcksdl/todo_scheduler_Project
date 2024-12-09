package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository{

    // 속성
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 기능
    // ::: 일정 생성
    @Override
    public TodoResponseDto createTodo(Todo todo) {

        // INSERT Query를 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("content", todo.getContent());
        parameters.put("writer", todo.getWriter());
        parameters.put("password", todo.getPassword());
        parameters.put("created_date", todo.getCreated_date());
        parameters.put("updated_date", todo.getUpdated_date());

        // 저장 후 생성된 key 값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(key.longValue(), todo.getTitle(), todo.getContent(), todo.getWriter(), todo.getCreated_date(), todo.getUpdated_date());
    }

    // ::: 전체 일정 조회
    @Override
    public List<TodoResponseDto> searchAllTodos(String updated_date, String writer) {

        String sql = "SELECT * FROM todo";

        if (updated_date != null && writer != null) {
            sql += " WHERE DATE(updated_date) = ? AND writer = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(sql, todoRowMapper(), updated_date, writer);
        } else if (updated_date != null) {
            sql += " WHERE DATE(updated_date) = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(sql, todoRowMapper(), updated_date);
        } else if (writer != null) {
            sql += " WHERE writer = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(sql, todoRowMapper(), writer);
        } else {
            sql += " ORDER BY updated_date DESC";
            return jdbcTemplate.query(sql, todoRowMapper());
        }
    }

    // ::: 선택 일정 조회
    @Override
    public Optional<Todo> searchTodoById(Long id) {

        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE id = ?", todoRowMapperV2(), id);

        return result.stream().findAny();
    }

    // ::: 선택 일정 조회 예외 처리
//    @Override
//    public Todo searchTodoByIdOrElseThrow(Long id) {
//
//        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE id = ?", todoRowMapperV2(), id);
//
//        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id));
//    }

    // ::: 선택 일정 수정
    @Override
    public int updateTodo(Long id, String title, String content, String writer, String password) {
        
        String sql = "UPDATE todo SET title = ?, content = ?, writer = ?, updated_date = ? WHERE id = ? AND password = ?";

        return jdbcTemplate.update(sql, title, content, writer, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), id, password);

    }

    // :: 선택 일정 삭제
    @Override
    public int deleteTodo(Long id, String password) {
        // 쿼리의 영향을 받은 row 수를 int로 반환받는다.
        return jdbcTemplate.update("DELETE FROM todo WHERE id = ? AND password = ?", id, password);
    }

    private RowMapper<TodoResponseDto> todoRowMapper() {

        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("writer"),
                        rs.getString("created_date"),
                        rs.getString("updated_date")
                );
            }
        };
    }

    private RowMapper<Todo> todoRowMapperV2() {

        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getString("created_date"),
                        rs.getString("updated_date")
                );
            }
        };
    }
}
