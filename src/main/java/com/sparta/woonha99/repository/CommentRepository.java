package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Comment;
import com.sparta.woonha99.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();
    List<Comment> findAllByPost(Post post);
    Long countByPost(Post post);
}
