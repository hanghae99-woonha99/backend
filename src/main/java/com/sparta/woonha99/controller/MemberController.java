package com.sparta.woonha99.controller;

import com.sparta.woonha99.dto.request.MemberRequestDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
