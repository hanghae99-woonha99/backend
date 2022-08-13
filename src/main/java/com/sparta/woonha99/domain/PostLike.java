package com.sparta.woonha99.domain;

import com.sparta.woonha99.dto.request.PostLikeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "post_like")
public class PostLike extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(name = "is_like", nullable = false)
    private boolean isLike;

    public void updatePostLike() {
        this.isLike = !this.isLike;
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
