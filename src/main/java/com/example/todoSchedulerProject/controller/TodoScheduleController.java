package com.example.todoSchedulerProject.controller;

import com.example.todoSchedulerProject.dto.TodoRequestDto;
import com.example.todoSchedulerProject.dto.TodoResponseDto;
import com.example.todoSchedulerProject.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Controller + @ResponseBody -> JSON 형태로 데이터를 응답하는 Controller에서 사용하는 Annotation.
@RequestMapping("/todos")  // 공통 Prefix URL 설정.
public class TodoScheduleController {

    // 속성
    /**
     * final 키워드를 사용하게 되면 최초로 설정된 TodoService가 애플리케이션 종료될 때까지 계속 유지된다.
     */
    private final TodoService todoService;

    // 생성자
    /**
     * TodoSchedulecontroller 생성자.
     * @param todoService - 비즈니스 로직을 처리하는 서비스 객체이다.
     */
    public TodoScheduleController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 기능
    // ::: 일정 생성 API
    /**
     * 새로운 일정을 생성하는 API.
     * @param todoRequestDto - 클라이언트로부터 전달받은 일정 생성 요청 데이터이다.
     * @return 생성된 일정 데이터와 상태 코드(HttpStatus.CREATED)
     */
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodoAPI(@RequestBody TodoRequestDto todoRequestDto) {

        return new ResponseEntity<>(todoService.createTodoService(todoRequestDto),HttpStatus.CREATED);
    }

    // ::: 전체 일정 조회 API
    /**
     * 저장된 모든 일정을 조회하는 API.
     * 수정일과 작성자명을 활용해서 조건에 따른 목록 조회가 가능하다.
     * @param updated_date - 수정일
     * @param writer - 작성자명
     * @return 일정 리스트와 상태 코드(HttpStatus.OK)
     */
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> searchAllTodosAPI(
            @RequestParam(required = false) String updated_date,
            @RequestParam(required = false) String writer
    ){
        return new ResponseEntity<>(todoService.searchAllTodosService(updated_date, writer), HttpStatus.OK);
    }

    // ::: 선택 일정 조회 API
    /**
     * 특정 ID를 가진 일정을 조회하는 API.
     * @param id - 일정의 고유 식별자
     * @return 해당되는 일정 데이터와 상태 코드(HttpStatus.OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> searchTodoByIdAPI(@PathVariable Long id) {


        return new ResponseEntity<>(todoService.searchTodoByIdService(id), HttpStatus.OK);
    }

    // ::: 선택 일정 수정 API
    /**
     * 특정 ID를 가진 일정을 수정하는 API.
     * 데이터베이스에 저장된 비밀번호와 일치할 경우에만 수정 가능하다.
     * @param id - 일정의 고유 식별자
     * @param todoRequestDto - 클라이언트로부터 전달받은 일정 수정 요청 데이터이다.
     * @return 수정된 일정 데이터와 상태 코드(HttpStatus.OK)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoAPI(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
       return new ResponseEntity<>(todoService.updateTodoService(id, todoRequestDto.getTitle(), todoRequestDto.getContent(), todoRequestDto.getWriter(), todoRequestDto.getPassword()), HttpStatus.OK);
    }

    // ::: 선택 일정 삭제 API
    /**
     * 특정 ID를 가진 일정을 삭제하는 API.
     * 저장된 비밀번호와 일치할 경우에만 삭제 가능하다.
     * @param id - 일정의 고유 식별자
     * @param todoRequestDto - 클라이언트로부터 전달받은 일정 삭제 요청 데이터이다. (비밀번호가 포함되어 있다.)
     * @return 상태 코드(HttpStatus.OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoAPI(
            @PathVariable Long id,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
        todoService.deleteTodoService(id, todoRequestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


