package com.estsoft.blogjpa.domain.comment.service;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.repository.ArticleRepository;
import com.estsoft.blogjpa.domain.comment.dto.CommentPostRequest;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import com.estsoft.blogjpa.domain.comment.dto.CommentUpdateRequest;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import com.estsoft.blogjpa.domain.comment.repository.CommentJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
@Transactional
@Slf4j
public class CommentService {

    private final CommentJpaRepository commentJpaRepository;
    private final ArticleRepository articleRepository;

    public CommentResponse createComment(CommentPostRequest request) {

        log.info("createComment()");

        Comment savedComment = commentJpaRepository.save(createCommentFromRequest(request));

        return CommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {

        log.info("findById()");

        return commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 comment id" + commentId));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> readComments() {

        log.info("readAllComment()");

        List<Comment> comments = commentJpaRepository.findAll();

        return CommentResponse.listFrom(comments);
    }

    public CommentResponse updateComment(CommentUpdateRequest request, Long commentId) {

        log.info("updateComment()");

        Comment comment = findById(commentId);
        comment.update(request.getContent());

        return CommentResponse.from(comment);
    }

    public void deleteComment(Long commentId) {

        log.info("deleteComment()");

        commentJpaRepository.deleteById(commentId);
    }

    private Comment createCommentFromRequest(CommentPostRequest request) {

        log.info("createCommentFromRequest()");

        Long articleId = request.getArticleId();
        Article article = findArticleById(articleId);

        return Comment.builder()
                .article(article)
                .content(request.getContent())
                .build();
    }

    private Article findArticleById(Long articleId) {

        log.info("findArticleById()");

        return articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 article id " + articleId));
    }

}
