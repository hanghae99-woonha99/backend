package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.Member;
import com.sparta.woonha99.domain.Post;
import com.sparta.woonha99.domain.PostLike;
import com.sparta.woonha99.dto.response.LikeResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.repository.PostLikeRepository;
import com.sparta.woonha99.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostService postService;
    private final PostLikeRepository postLikeRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> togglePostLikeByPostId(Long postId, HttpServletRequest request) {

//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember();
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        PostLike postLike = isPresentPostLikeByPost(member, post);

        if (null == postLike) {
            // 좋아요가 안되어있음
            postLikeRepository.save(
                    PostLike.builder()
                            .member(member)
                            .post(post)
                            .build()
            );
            return ResponseDto.success(
                    LikeResponseDto.builder()
                            .isLike(true)
                            .postLikeCnt(postLikeRepository.countByPost(post))
                            .msg("게시글 좋아요 성공")
                            .build()
            );
        } else {
            // 좋아요가 되어있음
            postLikeRepository.delete(postLike);
            return ResponseDto.success(
                    LikeResponseDto.builder()
                            .isLike(false)
                            .postLikeCnt(postLikeRepository.countByPost(post))
                            .msg("게시글 좋아요 취소 성공")
                            .build()
            );
        }
    }

    @Transactional(readOnly = true)
    public PostLike isPresentPostLikeByPost(Member member, Post post) {
        Optional<PostLike> optionalLike = postLikeRepository.findByMemberAndPost(member, post);
        return optionalLike.orElse(null);
    }

    @Transactional
    public Member validateMember() {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
