package com.estsoft.blogjpa.domain.article.controller.api;

import com.estsoft.blogjpa.common.wrapper.ApiResponse;
import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponse;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponseWithComments;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/article")
    public ResponseEntity<ArticleResponseWithComments> addArticle(@RequestBody ArticlePostRequest request) {

        log.info("addArticle()");

        Article article = articleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleToArticleResponseWithComments(article));
    }

    @GetMapping("/api/articles/with-comments")
    public ResponseEntity<ApiResponse<List<ArticleResponseWithComments>>> showArticles() {

        log.info("showArticles()");

        List<Article> articleList = articleService.readWithComments();

        List<ArticleResponseWithComments> responseList = articleList.stream()
                .map(this::articleToArticleResponseWithComments)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(responseList.size(), responseList));
    }

    @GetMapping("/api/article/{articleId}/with-comments")
    public ResponseEntity<ArticleResponseWithComments> showOneArticleWithComments(@PathVariable Long articleId) {

        log.info("showOneArticle()");

        Article articleWithComments = articleService.readWithComments(articleId);

        return ResponseEntity.ok(articleToArticleResponseWithComments(articleWithComments));
    }

    @GetMapping("/api/article/{articleId}")
    public ResponseEntity<ArticleResponse> showOneArticle(@PathVariable Long articleId) {

        log.info("showOneArticle()");

        Article article = articleService.read(articleId);

        return ResponseEntity.ok(articleToArticleResponse(article));
    }

    @PutMapping("/api/article/{articleId}")
    public ResponseEntity<ArticleResponseWithComments> updateOneArticle(@PathVariable Long articleId,
                                                                        @RequestBody ArticlePostRequest request) {

        log.info("updateOneArticle() - id: {}", articleId);

        Article updated = articleService.update(articleId, request);

        return ResponseEntity.ok(articleToArticleResponseWithComments(updated));
    }

    @DeleteMapping("/api/article/{articleId}")
    public ResponseEntity<?> deleteOneArticle(@PathVariable Long articleId) {

        log.info("deleteOneArticle()");

        articleService.deleteById(articleId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private ArticleResponseWithComments articleToArticleResponseWithComments(Article article) {

        log.info("articleToArticleResponse()");

        List<CommentResponse> commentResponses = article
                .getComments()
                .stream()
                .map(CommentResponse::from)
                .toList();

        ApiResponse<List<CommentResponse>> response = new ApiResponse<>(article.getComments().size(), commentResponses);

        return ArticleResponseWithComments.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .comments(response)
                .build();

    }

    private ArticleResponse articleToArticleResponse(Article article) {

        log.info("articleToArticleResponse()");

        return ArticleResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

}
