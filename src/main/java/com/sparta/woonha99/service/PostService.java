package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.Member;
import com.sparta.woonha99.domain.Post;
import com.sparta.woonha99.domain.PostLike;
import com.sparta.woonha99.dto.request.PostRequestDto;
import com.sparta.woonha99.dto.response.PostResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.jwt.TokenProvider;
import com.sparta.woonha99.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "refresh token is invalid");
        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .descript(requestDto.getDescript())
                .imgUrl(requestDto.getImgUrl())
                .member(member)
                .build();
        postRepository.save(post);

//        PostLike postLike = PostLike.builder()
//                .member(member)
//                .post(post)
//                .isLike(false)
//                .build();
//        postLikeRepository.save(postLike);

        return ResponseDto.success("게시물 등록 완료");
    }

    @Transactional
    public ResponseDto<?> updatePost(Long postId, PostRequestDto requestDto, HttpServletRequest request) {
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "refresh token is invalid");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }


//        if (postRepository.findById(postId).get().getMember().getMemberId() != member.getMemberId()) {
//            System.out.println(postRepository.findById(postId).get().getMember().getMemberId());
//            System.out.println(member.getMemberId());
//            return ResponseDto.fail("BAD_REQUEST", "only author can update");
//        }
        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);
        return ResponseDto.success("게시글 수정 성공");
    }

    @Transactional
    public ResponseDto<?> deletePost(Long postId, HttpServletRequest request) {
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "refresh token is invalid");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }

//        if (postRepository.findById(postId).get().getMember().getMemberId() != member.getMemberId()) {
//            return ResponseDto.fail("BAD_REQUEST", "only author can delete");
//        }


        postRepository.delete(post);
        return ResponseDto.success("게시물 삭제 완료");
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
