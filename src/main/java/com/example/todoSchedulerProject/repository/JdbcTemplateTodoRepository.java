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
    /**
     * 데이터베이스 작업을 수행하기 위한 JdbcTemplate 객체이다.
     */
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    /**
     * JdbcTemplateTodoRepository 생성자.
     * @param dataSource - 데이터 소스 객체 (JdbcTemplate 초기화를 위해 필요하다.)
     */
    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 기능
    // ::: 일정 생성
    /**
     * 새로운 일정을 데이터베이스에 저장하는 메서드.
     * @param todo - 저장할 일정 객체
     * @return 생성된 일정의 응답 데이터 (TodoResponseDto)
     */
    @Override
    public TodoResponseDto createTodo(Todo todo) {

        // SimpleJdbcInsert를 사용해서 INSERT 쿼리를 간편하게 실행한다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        // 입력할 데이터를 HashMap에 저장한다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("content", todo.getContent());
        parameters.put("writer", todo.getWriter());
        parameters.put("password", todo.getPassword());
        parameters.put("created_date", todo.getCreated_date());
        parameters.put("updated_date", todo.getUpdated_date());

        // INSERT 쿼리 실행 후 생성된 key 값을 Number 타입으로 반환받아서 일정 응답 객체로 변환한다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(key.longValue(), todo.getTitle(), todo.getContent(), todo.getWriter(), todo.getCreated_date(), todo.getUpdated_date());
    }

    // ::: 전체 일정 조회
    /**
     * 조건에 맞는 모든 일정을 조회하는 메서드.
     * @param updated_date - 조회할 일정의 수정일 ("YYYY-MM-DD")
     * @param writer - 조회할 일정의 작성자명
     * @return 조건에 맞는 일정 리스트
     */
    @Override
    public List<TodoResponseDto> searchAllTodos(String updated_date, String writer) {

        String selectAllTodosSql = "SELECT * FROM todo";

        // 조건에 따라 WHERE 절을 추가한다.
        if (updated_date != null && writer != null) {
            selectAllTodosSql += " WHERE DATE(updated_date) = ? AND writer = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(selectAllTodosSql, todoRowMapper(), updated_date, writer);
        } else if (updated_date != null) {
            selectAllTodosSql += " WHERE DATE(updated_date) = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(selectAllTodosSql, todoRowMapper(), updated_date);
        } else if (writer != null) {
            selectAllTodosSql += " WHERE writer = ? ORDER BY updated_date DESC";
            return jdbcTemplate.query(selectAllTodosSql, todoRowMapper(), writer);
        } else {
            selectAllTodosSql += " ORDER BY updated_date DESC";
            return jdbcTemplate.query(selectAllTodosSql, todoRowMapper());
        }
    }

    // ::: 선택 일정 조회
    /**
     * 특정 id를 가진 일정을 조회하는 메서드.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정 (Optional 형태로 반환한다.)
     */
    @Override
    public Optional<Todo> searchTodoById(Long id) {

        String searchTodoByIdSql = "SELECT * FROM todo WHERE id = ?";

        List<Todo> result = jdbcTemplate.query(searchTodoByIdSql, todoRowMapperV2(), id);

        return result.stream().findAny();
    }

    // ::: 선택 일정 조회 예외 처리
    /**
     * 특정 id를 가진 일정을 조회하고, 없을 경우에는 예외를 발생시키는 메서드.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정 객체
     * @throws ResponseStatusException 데이터가 존재하지 않을 때 발생한다.
     */
    @Override
    public Todo searchTodoByIdOrElseThrow(Long id) {

        String searchTodoByIdSql = "SELECT * FROM todo WHERE id = ?";

        List<Todo> result = jdbcTemplate.query(searchTodoByIdSql, todoRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id));
    }

    // ::: 선택 일정 수정
    /**
     * 특정 id를 가진 일정을 수정하는 메서드.
     * @param id - 수정할 일정의 고유 식별자
     * @param title - 수정할 일정의 제목
     * @param content - 수정할 일정의 내용
     * @param writer - 수정할 일정의 작성자명
     * @param password - 일정의 비밀번호
     * @return 수정된 row의 수
     */
    @Override
    public int updateTodo(Long id, String title, String content, String writer, String password) {
        
        String updateTodoSql = "UPDATE todo SET title = ?, content = ?, writer = ?, updated_date = ? WHERE id = ? AND password = ?";

        // 쿼리의 영향을 받은 row 수를 int로 반환받는다.
        return jdbcTemplate.update(updateTodoSql, title, content, writer, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), id, password);

    }

    // :: 선택 일정 삭제
    /**
     * 특정 id를 가진 일정을 삭제하는 메서드.
     * @param id - 삭제할 일정의 고유 식별자
     * @param password - 삭제할 일정의 비밀번호
     * @return 삭제된 row의 수
     */
    @Override
    public int deleteTodo(Long id, String password) {

        String deleteTodoSql = "DELETE FROM todo WHERE id = ? AND password = ?";

        // 쿼리의 영향을 받은 row 수를 int로 반환받는다.
        return jdbcTemplate.update(deleteTodoSql, id, password);
    }

    // ::: Rowmapper - TodoResponseDto
    /**
     * 데이터베이스 결과(ResultSet)를 TodoResponseDto로 변환하는 RowMapper.
     * @return RowMapper<TodoResponseDto>
     */
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

    // ::: RowMapper - Todo
    /**
     * 데이터베이스 결과(ResultSet)를 Todo 객체로 변환하는 RowMapper.
     * @return RowMapper<Todo>
     */
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
