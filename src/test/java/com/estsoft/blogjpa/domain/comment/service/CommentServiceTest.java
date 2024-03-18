package com.estsoft.blogjpa.domain.comment.service;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import com.estsoft.blogjpa.domain.comment.dto.CommentRequest;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    ArticleService articleService;

    @Test
    void create() {
        // given
        CommentRequest request = new CommentRequest(1L, "content505050");

        // when
        commentService.createComment(request);

        // then
        Article article = articleService.readWithComments(1L);
        List<Comment> comments = article.getComments();
        // comments를 돌면서 content가 일치하는 comment가 있는지 확인
        assertThat(comments).extracting("content").contains("content505050");
    }

    @Test
    void findById() {
        // given
        CommentRequest request = new CommentRequest(1L, "content505050");
        CommentResponse comment = commentService.createComment(request);

        // when
        Comment foundComment = commentService.findById(comment.getId());

        // then
        assertThat(foundComment.getContent()).isEqualTo(comment.getContent());
    }

    @Test
    void readComments() {
        // given
        CommentRequest request = new CommentRequest(1L, "content505050");
        commentService.createComment(request);

        // when
        List<CommentResponse> comments = commentService.readComments();

        // then
        for (CommentResponse comment : comments) {
            log.info("comment = {}", comment);
        }
        assertThat(comments).extracting("content").contains("content505050");
    }

    @Test
    void updateComment() {
        // given
        CommentRequest request = new CommentRequest(1L, "content505050");
        CommentResponse createResponse = commentService.createComment(request);

        // when
        CommentResponse commentResponse = commentService.updateComment(new CommentRequest(1L, "updatedContent"), createResponse.getId());
        Comment comment = commentService.findById(commentResponse.getId());

        // then
        assertThat(comment.getContent()).isEqualTo("updatedContent");
    }
}