package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPost(Post post);
    Long countByPost(Post post);
}
