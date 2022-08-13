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
public class MemberResponseDto {
  private Long memberId;
  private String nickname;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private String msg;
}
