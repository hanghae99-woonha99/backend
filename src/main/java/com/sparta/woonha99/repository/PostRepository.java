package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Member;
import com.sparta.woonha99.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

}
