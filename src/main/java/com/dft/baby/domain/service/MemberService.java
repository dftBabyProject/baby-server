package com.dft.baby.domain.service;

import com.dft.baby.domain.dto.member.req.MemberAppleCreateRequestDto;
import com.dft.baby.domain.dto.member.req.MemberCreateRequestDto;
import com.dft.baby.domain.entity.member.Auth;
import com.dft.baby.domain.entity.member.Member;
import com.dft.baby.domain.generator.CodeGenerator;
import com.dft.baby.domain.redisService.LoginRedisService;
import com.dft.baby.domain.repository.AuthRepository;
import com.dft.baby.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final CodeGenerator codeGenerator;

    @Value("${admin.id}")
    private String adminId;

    /*
     * 회원저장
     * */
    public Member createMember(MemberCreateRequestDto dto, String socialId) {
        Member member = new Member(dto, codeGenerator.generateBase64Id(), socialId);
        Auth auth = new Auth(member);
        return memberRepository.save(member);
    }

    /*
     * 회원저장
     * */
    public Member createAppleMember(MemberAppleCreateRequestDto dto) {
        Member member = new Member(dto, codeGenerator.generateBase64Id(), dto.getUser());
        Auth auth = new Auth(member);
        return memberRepository.save(member);
    }
}
