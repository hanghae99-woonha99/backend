package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.request.PostRequestDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/auth/posts")
    public ResponseDto<?> createPost(@RequestPart("data") PostRequestDto requestDto,
                                     HttpServletRequest request,
                                     @RequestPart("image") MultipartFile multipartFile) throws IOException {
        return postService.createPost(requestDto, request, multipartFile);
    }

    @GetMapping("/posts")
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{postId}")
    public ResponseDto<?> getPost(@PathVariable Long postId) {
        return postService.getPostByPostId(postId);
    }
    
    @PutMapping("/auth/posts/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId,
                                     @RequestPart("data") PostRequestDto requestDto,
                                     @RequestPart("image") MultipartFile multipartFile,
                                     HttpServletRequest request) throws IOException {
        return postService.updatePostByPostId(postId, requestDto, multipartFile, request);
    }

    @DeleteMapping("/auth/posts/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePostByPostId(postId, request);
    }
}
