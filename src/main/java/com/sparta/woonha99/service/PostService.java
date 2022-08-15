package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.Comment;
import com.sparta.woonha99.domain.Member;
import com.sparta.woonha99.domain.Post;
import com.sparta.woonha99.domain.PostLike;
import com.sparta.woonha99.dto.request.PostRequestDto;
import com.sparta.woonha99.dto.response.CommentResponseDto;
import com.sparta.woonha99.dto.response.PostResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.jwt.TokenProvider;
import com.sparta.woonha99.repository.CommentLikeRepository;
import com.sparta.woonha99.repository.CommentRepository;
import com.sparta.woonha99.repository.PostLikeRepository;
import com.sparta.woonha99.repository.PostRepository;
import com.sparta.woonha99.shared.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final TokenProvider tokenProvider;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto,
                                     HttpServletRequest request,
                                     MultipartFile multipartFile) throws IOException {

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

        String imgUrl = "";
        if (!multipartFile.isEmpty()) {
            imgUrl = s3Uploader.upload(multipartFile, "static");
            System.out.println("imgUrl: "+imgUrl);
        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .descript(requestDto.getDescript())
                .imgUrl(imgUrl)
                .member(member)
                .build();
        postRepository.save(post);

        PostLike postLike = PostLike.builder()
                .member(member)
                .post(post)
                .isLike(false)
                .build();
        postLikeRepository.save(postLike);

        return ResponseDto.success(
            PostResponseDto.builder()
                    .msg("게시글 작성 완료")
                    .postLikesCnt(null)
                    .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPosts() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(
                    PostResponseDto.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .descript(post.getDescript())
                            .imgUrl(post.getImgUrl())
                            .author(post.getMember().getNickname())
                            .postLikesCnt(postLikeRepository.countByPost(post))
                            .commentsCnt(commentRepository.countByPost(post))
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }

        return ResponseDto.success(postResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> getPostByPostId(Long postId) {
        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .commentId(comment.getCommentId())
                            .author(comment.getMember().getNickname())
                            .descript(comment.getDescript())
                            .commentLikesCnt(commentLikeRepository.countByComment(comment))
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .descript(post.getDescript())
                        .commentResponseDtoList(commentResponseDtoList)
                        .author(post.getMember().getNickname())
                        .imgUrl(post.getImgUrl())
                        .postLikesCnt(postLikeRepository.countByPost(post))
                        .commentsCnt(commentRepository.countByPost(post))
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> updatePostByPostId(Long postId, PostRequestDto requestDto,
                                             MultipartFile multipartFile, HttpServletRequest request) throws IOException {
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

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        String imgUrl = post.getImgUrl();
        if (!multipartFile.isEmpty()) {
            imgUrl = s3Uploader.upload(multipartFile, "static");
            System.out.println("imgUrl: "+imgUrl);
        } else {
            imgUrl = "";
        }

        post.update(requestDto, imgUrl);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .msg("게시물 수정 완료")
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deletePostByPostId(Long postId, HttpServletRequest request) {
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

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .msg("게시물 삭제 완료")
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
