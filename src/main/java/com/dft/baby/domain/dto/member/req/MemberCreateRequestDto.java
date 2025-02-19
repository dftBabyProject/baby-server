package com.dft.baby.domain.dto.member.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MemberCreateRequestDto {

    private String accessToken;
    private String nickname;
}
