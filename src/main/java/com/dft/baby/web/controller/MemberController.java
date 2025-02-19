package com.dft.baby.web.controller;

import com.dft.baby.domain.dto.member.req.MemberAppleCreateRequestDto;
import com.dft.baby.domain.dto.member.req.MemberCreateRequestDto;
import com.dft.baby.domain.dto.member.res.TokenResponseDto;
import com.dft.baby.domain.entity.member.Member;
import com.dft.baby.domain.service.LoginService;
import com.dft.baby.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    /*카카오 로그인*/
    @PostMapping("/login/kakao")
    public void kakaoLogin() {}

    /*애플 로그인*/
    @PostMapping("/login/apple")
    public void appleLogin() {}

    /*카카오 회원 가입*/
    @PostMapping("/kakao")
    public void createMember(@RequestBody MemberCreateRequestDto dto) {
        String socialId = loginService.accessToKakao(dto.getAccessToken());
        Member member = memberService.createMember(dto, socialId);
        TokenResponseDto tokenList = loginService.createToken(member);
    }

    /*애플 회원 가입*/
    @PostMapping("/apple")
    public void createAppleMember(@RequestBody MemberAppleCreateRequestDto dto) {
        loginService.accessToApple(dto.getIdToken(), dto.getUser());
        Member member = memberService.createAppleMember(dto);
        TokenResponseDto tokenList = loginService.createToken(member);
    }
}
