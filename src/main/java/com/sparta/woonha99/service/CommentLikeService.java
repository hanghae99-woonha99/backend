package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.*;
import com.sparta.woonha99.dto.response.LikeResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.jwt.TokenProvider;
import com.sparta.woonha99.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentService commentService;
    private final CommentLikeRepository commentLikeRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> toggleCommentLikeByCommentId(Long commentId, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        CommentLike commentLike = isPresentCommentLikeByComment(member, comment);

        if (null == commentLike) {
            commentLikeRepository.save(
                    CommentLike.builder()
                            .member(member)
                            .comment(comment)
                            .build()
            );
            return ResponseDto.success(
                    LikeResponseDto.builder()
                            .isLike(true)
                            .commentLikeCnt(commentLikeRepository.countByComment(comment))
                            .msg("댓글 좋아요 성공")
                            .build()
            );
        } else {
            commentLikeRepository.delete(commentLike);
            return ResponseDto.success(
                    LikeResponseDto.builder()
                            .isLike(false)
                            .commentLikeCnt(commentLikeRepository.countByComment(comment))
                            .msg("댓글 좋아요 취소 성공")
                            .build()
            );
        }

    }

    @Transactional(readOnly = true)
    public CommentLike isPresentCommentLikeByComment(Member member, Comment comment) {
        Optional<CommentLike> optionalLike = commentLikeRepository.findByMemberAndComment(member, comment);
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
