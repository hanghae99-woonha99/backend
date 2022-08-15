package com.sparta.woonha99.domain;

import com.sparta.woonha99.dto.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String descript;

    @Column
    private String imgUrl;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes;

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.descript = postRequestDto.getDescript();
        this.imgUrl = postRequestDto.getImgUrl();
    }

//    public void update(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

}
