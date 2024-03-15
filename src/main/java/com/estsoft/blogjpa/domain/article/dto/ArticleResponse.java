package com.estsoft.blogjpa.domain.article.dto;

import com.estsoft.blogjpa.common.wrapper.ApiResponse;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {

    private Long articleId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ApiResponse<List<CommentResponse>> comments;

}
