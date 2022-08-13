package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.request.LoginRequestDto;
import com.sparta.woonha99.dto.request.MemberRequestDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

//    @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//        return memberService.reissue(request, response);
//    }
//
//    @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
//    public ResponseDto<?> logout(HttpServletRequest request) {
//        return memberService.logout(request);
//    }

}
