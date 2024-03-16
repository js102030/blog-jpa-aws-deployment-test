package com.estsoft.blogjpa.domain.article.service;

import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.domain.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void createArticle() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        Article findArticle = articleService.readWithComments(1L);

        assertEquals(1L, findArticle.getId());

    }

    @Test
    void updateArticle() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        ArticlePostRequest updateRequest = new ArticlePostRequest("update title", "update content");

        articleService.update(1L, updateRequest);

        Article updatedArticle = articleService.readWithComments(1L);

        assertEquals("update title", updatedArticle.getTitle());
        assertEquals("update content", updatedArticle.getContent());

    }

    @Test
    void deleteArticle() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        articleService.deleteById(1L);

        Article deletedArticle = articleService.readWithComments(1L);

        assertNull(deletedArticle);

    }

    @Test
    void saveBulkArticles() {

        articleService.saveBulkArticles();

        List<Article> articles = articleService.readAll();

        assertEquals(100, articles.size());

    }

    @Test
    void findAllArticles() {

        ArticlePostRequest request1 = new ArticlePostRequest("title1", "content1");
        ArticlePostRequest request2 = new ArticlePostRequest("title2", "content2");
        ArticlePostRequest request3 = new ArticlePostRequest("title3", "content3");
        ArticlePostRequest request4 = new ArticlePostRequest("title4", "content4");

        articleService.create(request1);
        articleService.create(request2);
        articleService.create(request3);
        articleService.create(request4);

        List<Article> articles = articleService.readWithComments();

        for (Article article : articles) {
            log.info("article = {}", article);
        }

        assertEquals(4, articles.size());

    }

    @Test
    void findArticleWithCommentsById() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        Article article = articleService.readWithComments(1L);

        log.info("article = {}",article);

    }

    @Test
    void findById() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        Article article = articleService.readWithComments(1L);

        assertEquals(1L, article.getId());

    }

    @Test
    void findAll() {

        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        articleService.create(request);

        Article article = articleService.readWithComments(1L);

        assertEquals(1L, article.getId());

    }


}