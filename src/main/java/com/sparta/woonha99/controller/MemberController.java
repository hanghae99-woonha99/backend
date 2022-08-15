package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.request.LoginRequestDto;
import com.sparta.woonha99.dto.request.MemberRequestDto;
import com.sparta.woonha99.dto.response.MemberResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/validate/nickname")
  public ResponseDto<?> validateNickname(@RequestBody MemberRequestDto requestDto) {
    return memberService.validateNickname(requestDto) ?
            ResponseDto.success(
                    MemberResponseDto.builder()
                            .valid(memberService.validateNickname(requestDto))
                            .msg("사용할 수 있는 아이디입니다.")
                            .build()
            ) :
            ResponseDto.fail("NICKNAME_DUPLICATED", "중복되는 아이디 입니다.");
  }

  @PostMapping("/signup")
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @PostMapping("/login")
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }

  @PostMapping("/logout")
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }
}
