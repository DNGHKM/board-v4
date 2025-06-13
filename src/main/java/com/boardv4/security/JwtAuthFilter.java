package com.boardv4.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 토큰을 확인, username을 request에 넣어줌
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Filter 수행, 로그인 시 request에 username 추가");
        String token = jwtUtil.resolveToken(request);

        if (token != null && jwtUtil.isValid(token)) {
            String username = jwtUtil.getUsername(token);
            request.setAttribute("username", username);
        }

        filterChain.doFilter(request, response);
    }
}
