package com.dft.baby.domain.dto.member.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogoutRequestDto {

    private Long memberId;

    public LogoutRequestDto() {
    }
}
