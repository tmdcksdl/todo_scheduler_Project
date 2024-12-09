package com.example.todoSchedulerProject.repository;

import com.example.todoSchedulerProject.domain.Todo;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    // 속성

    // 생성자

    // 기능
    /**
     * 새로운 일정을 데이터베이스에 저장하는 메서드.
     * @param todo - 저장할 일정 객체
     * @return 생성된 일정의 응답 데이터 (TodoResponseDto)
     */
    TodoResponseDto createTodo(Todo todo);

    /**
     * 조건에 따라 모든 일정을 조회하는 메서드.
     * @param updated_date - 조회할 수정일
     * @param writer - 조회할 작성자명
     * @return 조건에 맞는 일정 리스트
     */
    List<TodoResponseDto> searchAllTodos(String updated_date, String writer);

    /**
     * 특정 id를 가진 일정을 조회하는 메서드.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정 객체 (Optional 형태로 반환된다.)
     */
    Optional<Todo> searchTodoById(Long id);

    /**
     * 특정 id를 가진 일정을 조회하고, 없을 경우 예외를 발생시키는 메서드.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정 객체
     * @throws ResponseStatusException - 데이터가 존재하지 않을 경우 발생한다.
     */
    Todo searchTodoByIdOrElseThrow(Long id);

    /**
     * 특정 id를 가진 일정을 수정하는 메서드.
     * @param id - 수정할 일정의 고유 식별자
     * @param title - 수정할 일정의 제목
     * @param content - 수정할 일정의 내용
     * @param writer - 수정할 일정의 작성자명
     * @param password - 일정의 비밀번호
     * @return 수정된 row의 수
     */
    int updateTodo(Long id, String title, String content, String writer, String password);

    /**
     * 특정 id를 가진 일정을 삭제하는 메서드.
     * @param id - 삭제할 일정의 고유 식별자
     * @param password - 삭제할 일정의 비밀번호
     * @return 삭제된 row의 수
     */
    int deleteTodo(Long id, String password);
}
