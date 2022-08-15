package com.sparta.woonha99.dto.response;

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
    private Long commentId;
    private String author;
    private String descript;
    private Long commentLikesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
