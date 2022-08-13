package com.sparta.woonha99.repository;

import com.sparta.woonha99.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String username);
}
