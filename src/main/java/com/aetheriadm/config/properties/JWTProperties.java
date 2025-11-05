package com.aetheriadm.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * JWT(JSON Web Token) 및 관련 보안 설정 값을 외부 설정 파일(예: application.yml)로부터
 * 주입받기 위한 레코드 클래스입니다.
 *
 * <p>설정 파일에서 {@code jwt} 프리픽스({@code prefix = "jwt"})로 시작하는 속성들을
 * 이 레코드의 필드에 자동으로 바인딩합니다.</p>
 */
@ConfigurationProperties(prefix = "jwt")
public record JWTProperties(
        // JWT 서명 및 검증에 사용될 비밀 키 문자열입니다. (반드시 Base64 인코딩된 문자열이어야 함). HS256(256bit) 알고리즘 사용 시 최소 32바이트(256비트) 이상을 권장합니다.
        String secret,

        // 액세스 토큰의 유효 기간(만료 기간)을 나타내는 Duration 값입니다. (일반적으로 시간 단위로 설정)
        Duration accessTokenValidityInHour,

        // JWT의 exp, nbf, iat 클레임을 검증할 때 허용되는 시계 오차(Clock Skew) 범위입니다. 단위는 초
        int allowedClockSkewSeconds,

        // Spring Security의 권한(Authority)을 토큰 클레임에서 식별할 때 사용되는 키 이름입니다. (예: "roles")
        String authorityKey,

        // 액세스 토큰이 HTTP 헤더에 담길 때 사용되는 스키마 이름입니다 (예: "Bearer: ")
        String bearerHeader
) {
}