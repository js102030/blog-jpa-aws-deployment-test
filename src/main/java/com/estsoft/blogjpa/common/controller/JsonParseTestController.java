package com.estsoft.blogjpa.common.controller;

import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JsonParseTestController {
    private final ArticleService articleService;

    public JsonParseTestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/test")
    public List<Article> test() {
        return articleService.saveBulkArticles();
    }
}
