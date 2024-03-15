package com.estsoft.blogjpa.domain.article.controller.api;

import com.estsoft.blogjpa.common.wrapper.ApiResponse;
import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponse;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import com.estsoft.blogjpa.domain.comment.service.CommentService;
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
    private final CommentService commentService;

    @PostMapping("/api/article")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody ArticlePostRequest request) {

        log.info("addArticle()");

        Article article = articleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleToArticleResponse(article));
    }

    @GetMapping("/api/articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> showArticles() {

        log.info("showArticles()");

        List<Article> articleList = articleService.findAll();

        List<ArticleResponse> responseList = articleList.stream()
                .map(this::articleToArticleResponse)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(responseList.size(), responseList));
    }

    @GetMapping("/api/article/{articleId}")
    public ResponseEntity<ArticleResponse> showOneArticle(@PathVariable Long articleId) {

        log.info("showOneArticle()");

        Article article = articleService.findArticleWithCommentsById(articleId);

        return ResponseEntity.ok(articleToArticleResponse(article));
    }

    @PutMapping("/api/article/{articleId}")
    public ResponseEntity<ArticleResponse> updateOneArticle(@PathVariable Long articleId,
                                                            @RequestBody ArticlePostRequest request) {

        log.info("updateOneArticle() - id: {}", articleId);

        Article updated = articleService.update(articleId, request);

        return ResponseEntity.ok(articleToArticleResponse(updated));
    }

    @DeleteMapping("/api/article/{articleId}")
    public ResponseEntity<?> deleteOneArticle(@PathVariable Long articleId) {

        log.info("deleteOneArticle()");

        articleService.deleteById(articleId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private ArticleResponse articleToArticleResponse(Article article) {

        log.info("articleToArticleResponse()");

        List<CommentResponse> commentResponses = article
                .getComments()
                .stream()
                .map(CommentResponse::from)
                .toList();

        ApiResponse<List<CommentResponse>> response = new ApiResponse<>(article.getComments().size(), commentResponses);

        return ArticleResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .comments(response)
                .build();

    }

}
