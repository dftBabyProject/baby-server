package com.dft.baby.web.exception.member;

import lombok.Getter;


@Getter
public class MemberException extends RuntimeException {
    private final int code;
    private final String errorMessage;

    public MemberException(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
