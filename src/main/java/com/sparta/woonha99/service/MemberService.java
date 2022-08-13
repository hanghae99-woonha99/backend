package com.sparta.woonha99.service;

import com.sparta.woonha99.domain.Member;
import com.sparta.woonha99.dto.request.MemberRequestDto;
import com.sparta.woonha99.dto.response.MemberResponseDto;
import com.sparta.woonha99.dto.response.ResponseDto;
import com.sparta.woonha99.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME",
                    "중복된 아이디입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .email(requestDto.getEmail())
                .build();
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .msg("회원가입 성공")
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

}
