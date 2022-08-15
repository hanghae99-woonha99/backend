package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByComment(Comment comment);
    Long countByComment(Comment comment);
}
