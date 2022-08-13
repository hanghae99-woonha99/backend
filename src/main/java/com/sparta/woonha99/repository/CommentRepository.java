package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Comment;
import com.sparta.woonha99.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();
}
