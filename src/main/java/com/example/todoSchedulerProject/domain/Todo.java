package com.example.todoSchedulerProject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    public Todo(String title, String content, String writer, String password, String created_date, String updated_date) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }


    // 기능
}