package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
