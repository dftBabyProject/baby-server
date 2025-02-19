package com.dft.baby.web.filter.security;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.dft.baby.domain.service.BlacklistService;
import com.dft.baby.web.exception.CommonException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.dft.baby.web.exception.ExceptionType.BLOCKED_IP;
import static com.dft.baby.web.util.Util.getClientIp;

@Component
@Order(1)
@RequiredArgsConstructor
public class BlacklistFilter implements Filter {

    private final BlacklistService blacklistService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final Pattern MALICIOUS_PATTERN = Pattern.compile("[<>\"'%;()&+]|(\\.{2}/)", Pattern.CASE_INSENSITIVE);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String clientIp = getClientIp(httpRequest);
        String requestUri = httpRequest.getRequestURI();

        try {
            if (blacklistService.isBlacklisted(clientIp)) {
                httpRequest.setAttribute("exception", BLOCKED_IP);
                throw new CommonException(BLOCKED_IP.getCode(), BLOCKED_IP.getErrorMessage());
            }

            if (!isValidUrl(requestUri)) {
                blacklistService.addToBlacklist(clientIp);
                httpRequest.setAttribute("exception", BLOCKED_IP);
                throw new CommonException(BLOCKED_IP.getCode(), BLOCKED_IP.getErrorMessage());
            }

            if (isMaliciousQuery(httpRequest)) {
                blacklistService.addToBlacklist(clientIp);
                httpRequest.setAttribute("exception", BLOCKED_IP);
                throw new CommonException(BLOCKED_IP.getCode(), BLOCKED_IP.getErrorMessage());
            }

            chain.doFilter(request, response); }
        catch (Exception e) {
            authenticationEntryPoint.commence(httpRequest, httpResponse, new AuthenticationException(e.getMessage()) {});
        }
    }

    private boolean isValidUrl(String requestUri) {
        if (requestUri.startsWith("/auth")
                || requestUri.startsWith("/common")
                || requestUri.startsWith("/error")) {
            return true;
        }

        return false;
    }

    private boolean isMaliciousQuery(HttpServletRequest request) {
        return request.getParameterMap().values().stream()
                .flatMap(Stream::of)
                .anyMatch(value -> MALICIOUS_PATTERN.matcher(value).find());
    }
}
