package com.estsoft.blogjpa.domain.article.service;

import com.estsoft.blogjpa.domain.article.dto.ArticlePostRequest;
import com.estsoft.blogjpa.external.ExampleAPIClient;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.repository.ArticleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ExampleAPIClient apiClient;

    public Article create(ArticlePostRequest request) {
        return articleRepository.save(request.toEntity());
    }

    public Article read(Long articleId) {
        return findById(articleId);
    }

    public List<Article> readWithComments() {
        return articleRepository.findAllWithComments();
    }

    public List<Article> readAll() {
        return articleRepository.findAll();
    }

    public Article readWithComments(Long articleId) {
        return articleRepository.findArticleWithCommentsById(articleId);
    }

    public void deleteById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional
    public Article update(Long articleId, ArticlePostRequest request) {
        Article article = findById(articleId);
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    private Article findById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 article이 존재하지 않습니다. articleId : " + articleId));
    }

    @Transactional
    public List<Article> saveBulkArticles() {
        List<Article> articles = parsedArticles();
        return articleRepository.saveAll(articles);
    }

    private List<Article> parsedArticles_old() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String responseJson = apiClient.fetchDataFromApi(url);

        ObjectMapper objectMapper = new ObjectMapper();
        List<LinkedHashMap<String, String>> jsonMapList = new ArrayList<>();

        try {
            jsonMapList = objectMapper.readValue(responseJson, List.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse json", e.getMessage());
        }

        return jsonMapList.stream()
                .map(hashMap -> new Article(hashMap.get("title"), hashMap.get("body")))
                .toList();
    }

    private List<Article> parsedArticles() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String responseJson = apiClient.fetchDataFromApi(url);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();

        try {
            List<Map<String, String>> jsonMapList = objectMapper.readValue(responseJson, new TypeReference<>() {
            });

            articles = jsonMapList.stream()
                    .map(map -> new Article(map.get("title"), map.get("body")))
                    .toList();
        } catch (JsonProcessingException e) {
            log.error("Failed to parse json {}", e.getMessage());
        }

        return articles;
    }
}
