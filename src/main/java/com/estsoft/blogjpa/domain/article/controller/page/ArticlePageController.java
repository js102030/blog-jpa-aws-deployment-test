package com.estsoft.blogjpa.domain.article.controller.page;

import com.estsoft.blogjpa.domain.article.dto.ArticleResponse;
import com.estsoft.blogjpa.domain.article.entity.Article;
import com.estsoft.blogjpa.domain.article.service.ArticleService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ArticlePageController {
    private final ArticleService articleService;

    public ArticlePageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<Article> articles = articleService.readAll();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {

        Article article = articleService.readWithComments(id);

        model.addAttribute("article", article.toViewResponse());

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        log.info("newArticle() - id: {}", id);

        if (id == null) {  // 등록
            log.info("newArticle() - 등록");
            model.addAttribute("article", new ArticleResponse());
        } else {  // 수정
            log.info("newArticle() - 수정");
            Article article = articleService.readWithComments(id);
            model.addAttribute("article", new ArticleResponse(article));
        }

        return "newArticle";
    }
}
