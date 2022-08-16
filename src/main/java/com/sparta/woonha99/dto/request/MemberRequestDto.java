package com.sparta.woonha99.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

  @NotBlank
  @Size(min = 4, max = 12)
  @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
  private String nickname;

  @NotBlank
  @Size(min = 8, max = 20)
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,20}$") // 영문, 숫자 포함 8자 이상 20자 이하
  private String password;

  @NotBlank
  private String passwordConfirm;
}
