package com.estsoft.blogjpa.domain.article.repository;

import com.estsoft.blogjpa.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Modifying
    @Query("update Article set title = :title where id = :id")
    void updateTitle(Long id, String title);

    @Query("select a from Article a join fetch a.comments")
    List<Article> findAllWithComments();

    @Query("select a from Article a join fetch a.comments where a.id = :id")
    Article findArticleWithCommentsById(Long id);
}
