package com.estsoft.blogjpa.domain.article.service;

import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.domain.article.entity.Article;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void create() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");

        // when
        Article article = articleService.create(request);

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo(request.getTitle());
        assertThat(article.getContent()).isEqualTo(request.getContent());
    }

    @Test
    void read() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        Article article = articleService.create(request);

        // when
        Article foundArticle = articleService.read(article.getId());

        // then
        assertThat(foundArticle.getId()).isEqualTo(article.getId());
        assertThat(foundArticle.getTitle()).isEqualTo(article.getTitle());
        assertThat(foundArticle.getContent()).isEqualTo(article.getContent());
    }

    @Test
    void readAllWithComments() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        articleService.create(request);

        // when
        List<Article> articles = articleService.readAllWithComments();

        // then
        assertThat(articles.size()).isEqualTo(3);
    }

    @Test
    void readAll() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        articleService.create(request);

        // when
        List<Article> articles = articleService.readAll();

        // then
        assertThat(articles.size()).isEqualTo(4);
    }

    @Test
    void readWithComments() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        Article article = articleService.create(request);

        // when
        // then
        Assertions.assertThatThrownBy(()->articleService.readWithComments(article.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteById() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        Article article = articleService.create(request);

        // when
        Long id = article.getId();
        System.out.println("id💣💣 = " + id);
        articleService.deleteById(id);

        // then
        Assertions.assertThatThrownBy(() -> articleService.read(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void update() {
        // given
        ArticlePostRequest request = new ArticlePostRequest("title", "content");
        Article article = articleService.create(request);
        ArticlePostRequest updateRequest = new ArticlePostRequest("updated title", "updated content");

        // when
        Article updatedArticle = articleService.update(article.getId(), updateRequest);

        // then
        assertThat(updatedArticle.getId()).isEqualTo(article.getId());
        assertThat(updatedArticle.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(updatedArticle.getContent()).isEqualTo(updateRequest.getContent());
    }

}