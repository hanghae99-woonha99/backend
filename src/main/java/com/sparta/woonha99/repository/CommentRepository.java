package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
