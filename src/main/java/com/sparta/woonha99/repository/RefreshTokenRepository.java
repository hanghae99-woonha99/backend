package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.User;
import com.sparta.woonha99.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByUser(User User);
}
