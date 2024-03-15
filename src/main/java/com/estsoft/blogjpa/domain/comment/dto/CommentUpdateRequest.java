package com.estsoft.blogjpa.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUpdateRequest {

    private Long articleId;
    private String content;

}
