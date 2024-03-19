package com.estsoft.blogjpa.domain.article.controller.api;

import com.estsoft.blogjpa.common.wrapper.ApiResponse;
import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponse;
import com.estsoft.blogjpa.domain.article.dto.ArticleResponseWithComments;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import com.estsoft.blogjpa.domain.comment.dto.CommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponseWithComments> addArticle(@RequestBody ArticlePostRequest request) {

        log.info("addArticle()");

        Article article = articleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleToArticleResponseWithComments(article));
    }

    @GetMapping("/api/articles/with-comments")
    public ResponseEntity<ApiResponse<List<ArticleResponseWithComments>>> showArticles() {

        log.info("showArticles()");

        List<Article> articleList = articleService.readAllWithComments();

        List<ArticleResponseWithComments> responseList = articleList.stream()
                .map(this::articleToArticleResponseWithComments)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(responseList.size(), responseList));
    }

    @GetMapping("/api/articles/{articleId}/with-comments")
    public ResponseEntity<ArticleResponseWithComments> showOneArticleWithComments(@PathVariable Long articleId) {

        log.info("showOneArticle()");

        Article articleWithComment = articleService.readWithComments(articleId);

        return ResponseEntity.ok(articleToArticleResponseWithComments(articleWithComment));
    }

    @GetMapping("/api/articles/{articleId}")
    public ResponseEntity<ArticleResponse> showOneArticle(@PathVariable Long articleId) {

        log.info("showOneArticle()");

        Article article = articleService.read(articleId);

        return ResponseEntity.ok(articleToArticleResponse(article));
    }

    @PutMapping("/api/articles/{articleId}")
    public ResponseEntity<ArticleResponseWithComments> updateOneArticle(@PathVariable Long articleId,
                                                                        @RequestBody ArticlePostRequest request) {

        log.info("updateOneArticle() - id: {}", articleId);

        Article updated = articleService.update(articleId, request);

        return ResponseEntity.ok(articleToArticleResponseWithComments(updated));
    }

    @DeleteMapping("/api/articles/{articleId}")
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
