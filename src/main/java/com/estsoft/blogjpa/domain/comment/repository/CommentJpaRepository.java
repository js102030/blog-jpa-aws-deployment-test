package com.estsoft.blogjpa.domain.comment.repository;

import com.estsoft.blogjpa.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.article WHERE c.article.id = :articleId")
    List<Comment> findAllByArticleId(Long articleId);

}
