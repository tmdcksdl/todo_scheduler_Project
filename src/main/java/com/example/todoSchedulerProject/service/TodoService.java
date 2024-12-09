package com.example.todoSchedulerProject.service;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    // 속성

    // 생성자

    // 기능
    /**
     * 새로운 일정을 생성하는 서비스 로직.
     * @param todoRequestDto - 일정 생성 요청 데이터이다.
     * @return 생성된 일정의 응답 데이터(TodoResponseDto)
     */
    TodoResponseDto createTodoService(TodoRequestDto todoRequestDto);

    /**
     * 저장된 모든 일정을 조회하는 서비스 로직.
     * @param updated_date - 수정일
     * @param writer - 작성자명
     * @return 필터링된 일정 리스트(TodoResponseDto)
     */
    List<TodoResponseDto> searchAllTodosService(String updated_date, String writer);

    /**
     * 특정 ID를 가진 일정을 조회하는 서비스 로직.
     * @param id - 조회할 일정의 고유 식별자
     * @return 조회된 일정의 응답 데이터(TodoResponseDto)
     */
    TodoResponseDto searchTodoByIdService(Long id);

    /**
     * 특정 id를 가진 일정을 수정하는 서비스 로직.
     * @param id - 수정할 일정의 고유 식별자
     * @param title - 수정할 일정 제목
     * @param content - 수정할 일정 내용
     * @param writer - 수정할 작성자명
     * @param password - 수정할 일정의 비밀번호
     * @return 수정된 일정의 응답 데이터(TodoResponseDto)
     */
    TodoResponseDto updateTodoService(Long id, String title, String content, String writer, String password);

    /**
     * 특정 id를 가진 일정을 삭제하는 서비스 로직.
     * @param id - 삭제할 일정의 고유 식별자
     * @param password - 삭제할 일정의 비밀번호
     */
    void deleteTodoService(Long id, String password);
}
