package com.aetheriadm.jwt.application;

import com.aetheriadm.common.exception.BusinessException;
import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.config.properties.JWTProperties;
import com.aetheriadm.jwt.domain.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @AuthUser 어노테이션을 처리하는 ArgumentResolver입니다.
 * HTTP 요청에서 Access Token을 추출하고,
 * 토큰을 직접 파싱하여 사용자 ID로 변환 후 파라미터에 주입합니다.
 */
@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    // private final JwtTokenResolverPort jwtTokenResolverPort; // 제거
    private final JwtParser parser; // JwtParser를 직접 주입받음
    private final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private final String BEARER_PREFIX;

    /**
     * JwtKeyManager에서 서명 키가 설정된 JwtParser 인스턴스를 주입받습니다.
     *
     * @param jwtKeyManager JWT 서명 키와 파서를 관리하는 컴포넌트
     */
    public AuthUserArgumentResolver(
            JWTProperties jwtProperties,
            JwtKeyManager jwtKeyManager) {
        this.BEARER_PREFIX = jwtProperties.bearerHeader();
        this.parser = jwtKeyManager.getParser();
    }

    /**
     * 이 리졸버가 지원하는 파라미터인지 검사합니다.
     * 1. @AuthUser 어노테이션이 붙어있는가
     * 2. 파라미터 타입이 Long 인가
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthUserAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasAuthUserAnnotation && hasLongType;
    }

    /**
     * supportsParameter가 true를 반환하면 실행됩니다.
     * 실제 파라미터에 바인딩할 객체(userId)를 반환합니다.
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = extractToken(request);

        // 1. 토큰 파싱
        String subject = claims(accessToken).getSubject();

        // 2. Subject를 Long으로 변환
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException e) {
            throw new BusinessException(
                    ErrorMessage.JWT_SUBJECT_IS_NOT_NUMBER,
                    "JWT 토큰 값이 유효하지 않습니다."
            );
        }
    }

    /**
     * HttpServletRequest의 Authorization 헤더에서 "Bearer " 접두사를 제거하고
     * 순수한 Access Token 문자열을 반환합니다.
     *
     * @param request HTTP 요청
     * @return Access Token 문자열 (없을 경우 null)
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        // 토큰이 없는 경우 null을 반환합니다.
        // claims() 메서드에서 null 체크를 수행합니다.
        return null;
    }

    /**
     * 주어진 JWT 토큰을 파싱하고 서명을 검증하여 {@link Claims}(페이로드)를 추출합니다.
     *
     * @param token 파싱할 JWT 토큰 문자열입니다.
     * @return 토큰의 페이로드 정보를 담고 있는 {@code Claims} 객체입니다.
     * @throws BusinessException 토큰 문자열이 {@code null}이거나 비어있을 경우 {@code JWT_TOKEN_IS_EMPTY} 예외를 발생시킵니다.
     */
    private Claims claims(String token) {
        if (token == null || token.isBlank()) {
            throw new BusinessException(
                    ErrorMessage.JWT_TOKEN_IS_EMPTY,
                    "JWT 토큰이 비어있습니다."
            );
        }
        // parser를 사용하여 토큰을 해독하고 서명 검증을 수행
        return parser.parseClaimsJws(token).getBody();
    }
}