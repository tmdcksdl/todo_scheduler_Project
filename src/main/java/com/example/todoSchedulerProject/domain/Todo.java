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
    // 일정 고유 식별자 ID.
    private Long id;
    // 일정의 제목.
    private String title;
    // 일정의 내용.
    private String content;
    // 일정의 작성자명.
    private String writer;
    // 일정의 비밀번호.
    private String password;
    // 일정의 작성일.
    private String created_date;
    // 일정의 수정일.
    private String updated_date;

    // 생성자
    /**
     * 새로운 Todo를 생성하는 생성자 (id는 자동으로 추가된다.)
     * @param title - 일정의 제목
     * @param content - 일정의 내용
     * @param writer - 일정의 작성자명
     * @param password - 일정의 비밀번호
     */
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
    /**
     * Todo의 제목, 내용, 작성자명을 수정하는 메서드.
     * @param title - 수정할 일정의 제목
     * @param content - 수정할 일정의 내용
     * @param writer - 수정할 일정의 작성자명
     */
    public void updateTodo(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.updated_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
