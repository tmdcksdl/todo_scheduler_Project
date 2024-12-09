package com.example.todoSchedulerProject.dto;

import com.example.todoSchedulerProject.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    // 속성
    // 일정의 고유 식별자 id.
    private Long id;
    // 일정의 제목.
    private String title;
    // 일정의 내용.
    private String content;
    // 일정의 작성자명.
    private String writer;
    // 일정의 작성일.
    private String created_date;
    // 일정의 수정일.
    private String updated_date;

    // 생성자
    /**
     * Todo 객체를 기반으로 TodoResponseDto를 생성하는 생성자.
     * Todo Entity에서 데이터를 가져와서 DTO에 매핑한다.
     * @param todo - Todo Entity 객체 (일정 데이터)
     */
    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.writer = todo.getWriter();
        this.created_date = todo.getCreated_date();
        this.updated_date = todo.getUpdated_date();
    }

    // 기능
}
