package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.request.PostLikeRequestDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @GetMapping("/api/auth/posts/{postId}/likes")
    public ResponseDto<?> togglePostLike(@PathVariable Long postId, HttpServletRequest request) {
        return postLikeService.togglePostLikeByPostId(postId, request);
    }

}
