package com.sparta.woonha99.controller;

import com.sparta.woonha99.domain.Post;
import com.sparta.woonha99.dto.request.PostRequestDto;
import com.sparta.woonha99.dto.response.PostResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.jwt.TokenProvider;
import com.sparta.woonha99.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,HttpServletRequest request){
        return postService.createPost(requestDto,request);
    }
    
    @PutMapping("/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId,@RequestBody PostRequestDto requestDto,HttpServletRequest request){
        return postService.updatePost(postId,requestDto, request);
    }

    @DeleteMapping("{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePost(postId, request);
    }




}
