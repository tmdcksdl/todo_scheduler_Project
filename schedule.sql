CREATE TABLE todo
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '투두 식별자',
    title VARCHAR(50) NOT NULL COMMENT '투두 제목',
    content TEXT NOT NULL COMMENT '투두 내용',
    writer VARCHAR(20) NOT NULL COMMENT '작성자명',
    password VARCHAR(10) NOT NULL COMMENT '비밀번호',
    created_date DATETIME NOT NULL COMMENT '작성일',
    updated_date DATETIME NOT NULL COMMENT '수정일'
);