package com.estsoft.blogjpa.domain.comment.dto;

import com.estsoft.blogjpa.domain.comment.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponse {
    private Long id;
    private Long articleId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Long id, Long articleId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static List<CommentResponse> listFrom(List<Comment> comments) {

        return comments.stream()
                .map(comment ->
                        new CommentResponse(
                                comment.getId(),
                                comment.getArticle().getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getUpdatedAt()))
                .toList();

    }

    public static CommentResponse from(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());

    }
}
