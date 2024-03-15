package com.estsoft.blogjpa.domain.comment.repository;

import com.estsoft.blogjpa.domain.comment.entity.Comment;
import com.estsoft.blogjpa.domain.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final JPAQueryFactory qf;

    public List<Comment> findAllByArticleId(Long articleId) {
        QComment c = QComment.comment;
        return qf.selectFrom(c)
                .join(c.article)
                .fetchJoin()
                .where(c.article.id.eq(articleId))
                .fetch();
    }
}
