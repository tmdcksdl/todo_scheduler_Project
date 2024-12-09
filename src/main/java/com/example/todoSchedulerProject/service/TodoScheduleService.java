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

/**
 * Annotation @Service는 @Compnent와 같다. Spring Bean으로 등록하는다는 뜻이다.
 * Spring Bean으로 등록되면 다른 클래스에서 주입해서 사용할 수 있다.
 * 명시적으로 Service Layer 라는 것을 나타낸다.
 * 비즈니스 로직을 수행한다.
 */
@Service
public class TodoScheduleService implements TodoService{

    // 속성
    /**
     * final 키워드를 사용하게 되면 최초로 설정된 TodoRepository가 애플리케이션 종료될 때까지 계속 유지된다.
     */
    private final TodoRepository todoRepository;

    // 생성자
    /**
     * TodoScheduleService 생성자
     * @param todoRepository - 일정 데이터 관리를 위한 Repository 객체이다.
     */
    public TodoScheduleService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 기능
    // ::: 일정 생성 서비스
    /**
     * 새로운 일정을 생성하는 서비스 로직.
     * @param todoRequestDto - 일정 생성 요청 데이터이다.
     * @return 생성된 일정의 응답 데이터(TodoResponseDto)
     */
    @Override
    public TodoResponseDto createTodoService(TodoRequestDto todoRequestDto) {

        // 요청 받은 데이터로 일정 객체를 생성한다.
        Todo todo = new Todo(todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword());

        // 일정 객체를 생성하고 응답 Dto를 반환한다.
        return todoRepository.createTodo(todo);
    }

    // ::: 전체 일정 조회 서비스
    /**
     * 저장된 모든 일정을 조회하는 서비스 로직.
     * @param updated_date - 수정일
     * @param writer - 작성자명
     * @return 필터링된 일정 리스트(TodoResponseDto)
     */
    @Override
    public List<TodoResponseDto> searchAllTodosService(String updated_date, String writer) {

        // 리스트 형태의 TodoResponseDto를 바로 반환받는다.
        List<TodoResponseDto> allTodos = todoRepository.searchAllTodos(updated_date, writer);

        return allTodos;
    }

    // ::: 선택 일정 조회 서비스
    /**
     * 특정 ID를 가진 일정을 조회하는 서비스 로직.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정의 응답 데이터(TodoResponseDto)
     */
    @Override
    public TodoResponseDto searchTodoByIdService(Long id) {

        // id를 활용해서 일정을 조회한다. 일치하는 id가 없을 경우 예외가 발생한다.
        Todo todo = todoRepository.searchTodoByIdOrElseThrow(id);

        return new TodoResponseDto(todo);
    }

    // ::: 선택 일정 수정 서비스
    /**
     * 특정 id를 가진 일정을 수정하는 서비스 로직.
     * @param id - 수정할 일정의 고유 식별자
     * @param title - 수정할 일정 제목
     * @param content - 수정할 일정 내용
     * @param writer - 수정할 작성자명
     * @param password - 수정할 일정의 비밀번호
     * @return 수정된 일정의 응답 데이터(TodoResponseDto)
     */
    @Transactional
    @Override
    public TodoResponseDto updateTodoService(Long id, String title, String content, String writer, String password) {

        // 필수값을 검증한다.
        if (title == null || content == null || writer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목, 내용, 작성자명이 포함되어 있지 않습니다.");
        }

        // 일정을 수정한다.
        int updatedRow = todoRepository.updateTodo(id, title, content, writer, password);

        // 수정한 결과를 확인한다. 수정된 row가 0개일 경우 예외처리를 진행한다.
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }

        // 수정된 일정 데이터를 반환한다.
        return new TodoResponseDto(todoRepository.searchTodoById(id).get());
    }

    // ::: 선택 일정 삭제 서비스
    /**
     * 특정 id를 가진 일정을 삭제하는 서비스 로직.
     * @param id - 삭제할 일정의 고유 식별자
     * @param password - 삭제할 일정의 비밀번호
     */
    @Override
    public void deleteTodoService(Long id, String password) {

        // 선택한 일정을 삭제한다.
        int deletedRow = todoRepository.deleteTodo(id, password);

        // 삭제된 결과를 확인한다. 삭제된 row가 0개일 경우 예외처리를 진행한다.
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다. id = " + id);
        }
    }
}
