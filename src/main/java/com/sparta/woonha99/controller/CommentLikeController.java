package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @GetMapping("/api/auth/comments/{commentId}/likes")
    public ResponseDto<?> toggleCommentLike(@PathVariable Long commentId, HttpServletRequest request) {
        return commentLikeService.toggleCommentLikeByCommentId(commentId, request);
    }

}
