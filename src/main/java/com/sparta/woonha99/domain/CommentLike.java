package com.sparta.woonha99.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "comment_like")
public class CommentLike extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(name = "is_like", nullable = false)
    private Boolean isLike;

    public void updateCommentLike() {
        this.isLike = !this.isLike;
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
