package com.dft.baby.web.config;

import com.dft.baby.domain.service.LoginService;
import com.dft.baby.web.filter.authentication.CustomUserDetailsService;
import com.dft.baby.web.filter.authentication.JwtAppleAuthenticationFilter;
import com.dft.baby.web.filter.authentication.JwtKakaoAuthenticationFilter;
import com.dft.baby.web.filter.authorization.JwtAuthorizationRsaFilter;
import com.dft.baby.web.filter.exception.CustomAuthenticationEntryPoint;
import com.dft.baby.web.filter.exception.CustomAuthenticationFailureHandler;
import com.dft.baby.web.filter.security.BlacklistFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler authFailureHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final LoginService loginService;
    private final BlacklistFilter blacklistFilter;
    private final JwtAuthorizationRsaFilter jwtAuthorizationRsaFilter;

    private String[] permitAllUrlPatterns() {
        return new String[] { "/", "/auth/kakao", "/auth/apple","/common/version-check" };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                        requests.requestMatchers(permitAllUrlPatterns()).permitAll().anyRequest().authenticated())
                .exceptionHandling(handler -> handler.authenticationEntryPoint(authenticationEntryPoint));

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.userDetailsService(userDetailsService);

        JwtKakaoAuthenticationFilter jwtKakaoAuthenticationFilter = new JwtKakaoAuthenticationFilter(http, loginService);
        jwtKakaoAuthenticationFilter.setAuthenticationFailureHandler(authFailureHandler);
        jwtKakaoAuthenticationFilter.setFilterProcessesUrl("/auth/login/kakao");

        JwtAppleAuthenticationFilter jwtAppleAuthenticationFilter = new JwtAppleAuthenticationFilter(http, loginService);
        jwtAppleAuthenticationFilter.setAuthenticationFailureHandler(authFailureHandler);
        jwtAppleAuthenticationFilter.setFilterProcessesUrl("/auth/login/apple");

        http.addFilter(jwtKakaoAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationRsaFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilter(jwtAppleAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationRsaFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(blacklistFilter, CorsFilter.class);
        return http.build();
    }
}
