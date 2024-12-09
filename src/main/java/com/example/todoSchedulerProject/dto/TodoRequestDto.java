package com.example.todoSchedulerProject.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    // 속성
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


    // 기능
}
