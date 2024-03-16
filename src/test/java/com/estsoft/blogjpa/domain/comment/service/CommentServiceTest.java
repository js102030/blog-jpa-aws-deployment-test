package com.estsoft.blogjpa.domain.comment.service;

import com.estsoft.blogjpa.domain.comment.dto.CommentPostRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    void create() {
        // given
        CommentPostRequest request = new CommentPostRequest(1L, "content");

    }
}