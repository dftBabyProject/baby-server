package com.dft.baby.domain.dto.member.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberDeleteRequestDto {

    private List<Boolean> options;
    private String reason;

    public MemberDeleteRequestDto() {
    }
}
