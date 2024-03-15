package com.estsoft.blogjpa.domain.comment.controller.api;

import com.estsoft.blogjpa.common.wrapper.ApiResponse;
import com.estsoft.blogjpa.domain.comment.dto.CommentPostRequest;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import com.estsoft.blogjpa.domain.comment.dto.CommentUpdateRequest;
import com.estsoft.blogjpa.domain.comment.entity.Comment;
import com.estsoft.blogjpa.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentPostRequest request) {

        log.info("createComment()");

        CommentResponse comment = commentService.createComment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    @GetMapping("/api/comment/{commentId}")
    public ResponseEntity<CommentResponse> readComment(@PathVariable Long commentId) {

        log.info("readComment()");

        Comment comment = commentService.findById(commentId);

        return ResponseEntity
                .ok(CommentResponse.from(comment));
    }

    @GetMapping("/api/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> readComments() {

        log.info("readAllComments()");

        List<CommentResponse> commentsResponse = commentService.readComments();

        return ResponseEntity
                .ok(new ApiResponse<>(commentsResponse.size(), commentsResponse));
    }

    @PutMapping("/api/comment/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {

        log.info("updateComment() - id: {}", commentId);

        CommentResponse commentResponse = commentService.updateComment(request, commentId);

        return ResponseEntity
                .ok(commentResponse);
    }


    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {

        log.info("deleteComment()");

        commentService.deleteComment(commentId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
