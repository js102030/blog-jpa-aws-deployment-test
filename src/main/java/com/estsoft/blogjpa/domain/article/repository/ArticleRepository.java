package com.estsoft.blogjpa.domain.article.repository;

import com.estsoft.blogjpa.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * join fetch를 사용하면 연관된 엔티티가 없는 경우 해당 엔티티는 조회되지 않음.
     * Article에 Comment가 없는 경우, 해당 Article은 조회 결과에서 제외됨.
     */
    @Query("select a from Article a join fetch a.comments")
    List<Article> findAllWithComments();

    @Query("select a from Article a join fetch a.comments where a.id = :id")
    Article findArticleWithCommentsById(Long id);
}
