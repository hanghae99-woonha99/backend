package com.sparta.woonha99.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descript;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentLikesCnt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime modifiedAt;
}
