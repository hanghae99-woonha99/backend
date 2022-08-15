package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.*;
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
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        CommentLike commentLike = isPresentCommentLikeByComment(comment);
        commentLike.updateCommentLike();
        System.out.println("Comment Like : " + commentLike.isLike());

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        return commentLike.isLike() ?
                ResponseDto.success("댓글 좋아요 성공") :
                ResponseDto.success("댓글 좋아요 취소 성공");
    }

    @Transactional(readOnly = true)
    public CommentLike isPresentCommentLikeByComment(Comment comment) {
        Optional<CommentLike> optionalLike = commentLikeRepository.findByComment(comment);
        return optionalLike.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
