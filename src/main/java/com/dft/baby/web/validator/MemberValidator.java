package com.dft.baby.web.validator;

import com.dft.baby.web.exception.ExceptionType;
import com.dft.baby.web.exception.member.MemberException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import static com.dft.baby.web.exception.ExceptionType.TOKEN_INVALID;

public class MemberValidator {

    public static void validateAuthentication(Authentication authentication, HttpServletRequest request) {
        if (authentication == null) {
            ExceptionType exception = (ExceptionType) request.getAttribute("exception");
            throw new MemberException(exception.getCode(), exception.getErrorMessage());
        }

        if (null == authentication.getName()) {
            throw new MemberException(TOKEN_INVALID.getCode(), TOKEN_INVALID.getErrorMessage());
        }
    }
}
