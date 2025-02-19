package com.dft.baby.web.exception.handler;

import com.dft.baby.web.exception.CommonException;
import com.dft.baby.web.exception.ErrorResult;
import com.dft.baby.web.exception.member.MemberException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ErrorResult memberExceptionHandle(MemberException e, HttpServletRequest request) {
        e.printStackTrace();
        log.error("[MemberException] url: {} | errorMessage: {} | cause Exception: ",
                request.getRequestURL(), e.getMessage(), e.getCause());
        return new ErrorResult(e.getCode(), e.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonException.class)
    public ErrorResult commonExceptionHandle(CommonException e, HttpServletRequest request) {
        e.printStackTrace();
        log.error("[CommonException] url: {} | errorMessage: {} | cause Exception: ",
                request.getRequestURL(), e.getMessage(), e.getCause());
        return new ErrorResult(e.getCode(), e.getErrorMessage());
    }
}
