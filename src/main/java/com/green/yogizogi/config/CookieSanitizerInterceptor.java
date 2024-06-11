package com.green.yogizogi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

public class CookieSanitizerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() != null) {
            // 쿠키 값 검증 및 살균 로직
            Arrays.stream(request.getCookies()).forEach(cookie -> {
                String sanitizedValue = sanitize(cookie.getValue());
                cookie.setValue(sanitizedValue);
            });
        }
        return true; // 계속해서 다음 인터셉터 또는 컨트롤러로 요청을 전달
    }

    // 쿠키 값에서 특수 문자를 제거하거나 원하는 형태로 살균하는 메소드
    private String sanitize(String value) {
        // 예시: 모든 비알파벳, 비숫자 문자를 제거
        return value.replaceAll("[^a-zA-Z0-9]", "");
    }

}