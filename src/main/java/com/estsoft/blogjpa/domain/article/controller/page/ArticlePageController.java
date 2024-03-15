package com.estsoft.blogjpa.domain.article.controller.page;

import com.estsoft.blogjpa.domain.article.dto.ArticleViewResponse;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class ArticlePageController {
    private final ArticleService articleService;

    public ArticlePageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {

        Article article = articleService.findById(id);

        model.addAttribute("article", article.toViewResponse());

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        log.info("newArticle() - id: {}", id);

        if (id == null) {  // 등록
            model.addAttribute("article", new ArticleViewResponse());
        } else {  // 수정
            Article article = articleService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
