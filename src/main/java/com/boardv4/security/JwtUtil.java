package com.boardv4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.SECRET_KEY = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    //무기한 토큰 발급
    public String generateToken(String username) {
        return Jwts.builder()
                .claim("username", username)
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            getJwtParser().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public String getUsername(String token) {
        Claims claims = getJwtParser()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("username", String.class);
    }

    public Claims getClaims(String token) {
        try {
            return getJwtParser()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.error("토큰 오류: {}", e.getMessage());
            throw new JwtException("토큰 오류", e);
        }
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(SECRET_KEY).build();
    }
}
