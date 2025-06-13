package com.boardv4.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckAspect {
    //@LoginRequired 어노테이션이 붙은 메서드에서 로그인 확인
    @Around("@annotation(com.boardv4.annotation.LoginRequired)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getCurrentHttpRequest();
        String username = (String) request.getAttribute("username");

        log.info("AOP(@LoginRequired) 수행, 로그인 여부 확인, username = {}", username);

        if (ObjectUtils.isEmpty(username)) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return joinPoint.proceed();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }
}
