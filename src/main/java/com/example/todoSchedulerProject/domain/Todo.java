package com.example.todoSchedulerProject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class Todo {

    // 속성
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;
    private String created_date;
    private String updated_date;

    // 생성자
    public Todo(String title, String content, String writer, String password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.created_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updated_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 기능
    // ::: Todo 수정
    public void updateTodo(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.updated_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
