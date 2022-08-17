package com.sparta.woonha99.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descript;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imgUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postLikeCnt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentCnt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime modifiedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentResponseDto> commentResponseDtoList;
}
