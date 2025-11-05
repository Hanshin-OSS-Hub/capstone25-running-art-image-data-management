package com.aetheriadm.jwt.application;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * 이 클래스는 주어진 토큰의 서명(Signature)을 확인하고 구조적 유효성을 검사하여,
 * 토큰이 변조되지 않았고 만료되지 않았는지 등을 판단합니다.
 */
@Slf4j
@Component
public class JwtTokenValidator {
    private final SecretKey KEY;

    /**
     * {@code JwtTokenValidator}의 생성자입니다.
     *
     * @param jwtKeyManager JWT 서명 키를 관리하는 컴포넌트입니다.
     */
    public JwtTokenValidator(JwtKeyManager jwtKeyManager) {
        // 토큰 검증에 필요한 서명 키를 KeyManager로부터 주입받습니다.
        this.KEY = jwtKeyManager.getKey();
    }

    /**
     * 주어진 JWT 토큰 문자열의 유효성을 검증합니다.
     *
     * <p>검증에는 토큰 서명 확인, 만료일 확인, 구조적 유효성 확인 등이 포함됩니다.</p>
     *
     * @param token 유효성을 검사할 JWT 토큰 문자열입니다.
     * @return 토큰이 유효하면 {@code true}를, 서명 오류, 만료, 구조적 오류 등으로 인해 유효하지 않으면 {@code false}를 반환합니다.
     */
    public boolean validateToken(String token) {
        try {
            // Jwts.parserBuilder()를 사용하여 서명 키를 설정하고 토큰을 파싱 및 검증합니다.
            Jws<Claims> jwsClaims = Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = jwsClaims.getPayload(); // .getBody() 대신 .getPayload() 사용
            // 예외 없이 완료되면 유효한 토큰입니다.
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 유효하지 않은 JWT 서명
            log.info("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 토큰
            log.info("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 토큰
            log.info("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            // JWT 토큰이 잘못되었습니다 (문자열이 null이거나 비어있을 때)
            log.info("JWT 토큰이 잘못되었습니다.", e);
        }
        return false;
    }
}