package com.dft.baby.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    /**
     * Common Exception
     * */
    UNKNOWN_ERROR( 90000, "알수없는 에러가 발생하였습니다."),
    TOKEN_NOT_EXIST( 90100, "JWT Token이 존재하지 않습니다."),
    TOKEN_INVALID( 90100, "유효하지 않은 JWT Token 입니다."),
    TOKEN_EXPIRED( 91000, "토큰 만료기간이 지났습니다."),
    MULTI_LOGIN( 90101, "다른 기기에서 로그인 되었습니다."),
    BLOCKED_IP( 90200, "의심스러운 활동으로 IP가 잠시 차단되었어요! 관리자에게 문의해주세요."),
    NOT_AVAILABLE_NOW( 90202, "현재 많은 사용자로 인해 작성할수없어요! 잠시만 기다려주세요."),
    ENCRYPT_FAIL( 92000, "암호화에 실패하였습니다."),
    DECRYPT_FAIL( 92001, "복호화에 실패하였습니다."),
    BULK_UPDATE_FAIL(92002, "벌크 업데이트에 실패하였습니다."),
    VERSION_INVALID(92003, "버전 정보가 잘못되었습니다."),

    /**
     * Member Exception
     * */
    MEMBER_NOT_EXIST( 10000, "회원이 존재하지 않아요."),
    LOGIN_FAILED(10001, "로그인에 실패하였습니다."),
    APPLE_LOGIN_MATCH_KEY_ERROR(10002, "애플로그인 키 매치에 실패하였습니다."),
    SOCIAL_CONNECT_FAILED(10003, "소셜계정 연결에 실패하였습니다. 다시 시도해주세요."),
    INVALID_INVITE_CODE( 10006, "올바른 커플 코드를 입력해주세요."),


    /**
     * REDIS Exception
     * */
    REDIS_CONNECT_ERROR( 60000, "레디스 연결 문제가 발생했습니다"),
    REDIS_SAVE_ERROR( 60001, "레디스 저장 문제가 발생했습니다"),
    REDIS_ERROR( 60002, "레디스에서 알 수 없는 문제가 발생했습니다");

    /**
     * DTO Exception
     * */
    private final int code;
    private final String errorMessage;
}
