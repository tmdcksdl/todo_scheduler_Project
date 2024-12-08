package com.example.todoSchedulerProject.dto;

import com.example.todoSchedulerProject.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    // 속성
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String created_date;
    private String updated_date;

    // 생성자
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
