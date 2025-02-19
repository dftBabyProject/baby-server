package com.dft.baby.web.util;

import com.dft.baby.web.exception.member.MemberException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static com.dft.baby.web.exception.ExceptionType.MEMBER_NOT_EXIST;

@Slf4j
public class Util {

    /*상수*/
    public static final String KAKAO_API = "https://kapi.kakao.com/v2/user/me";
    public static final String APPLE_API = "https://appleid.apple.com/auth/keys";
    public static final String DEFAULT_PROFILE_IMAGE = "basicProfile.png";

    public static final Set<String> GET_ROUTE = Set.of("/couple/synchronize", "/couple");

    /*시간*/
    public static final long EPOCH = 1721865600000L;
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    public static final long ACCESS_TOKEN_EXPIRED = ONE_DAY * 30;
    public static final long REFRESH_TOKEN_EXPIRED = ONE_DAY * 60;

    public static final String ACCESS_TOKEN = "accessToken";

    /*OS*/
    public static final Long ANDROID = 0L;
    public static final Long IOS = 1L;

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index).trim();
            } else {
                return ip.trim();
            }
        }
        return request.getRemoteAddr();
    }

    public static Long parseLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new MemberException(MEMBER_NOT_EXIST.getCode(), MEMBER_NOT_EXIST.getErrorMessage());
        }
    }
}
