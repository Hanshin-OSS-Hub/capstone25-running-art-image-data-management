package com.aetheriadm.kakaotoken.interfaces.dto.response;

import com.aetheriadm.kakaotoken.domain.KakaoToken;

public record KakaoTokenResponse(
        Long id,
        Long runnerId,
        String accessToken,
        String refreshToken
) {
    /**
     * {@code KakaoToken} 엔티티를 {@code KakaoTokenResponse} Record로 변환하는 정적 팩토리 메서드입니다.
     *
     * @param kakaoToken 변환할 {@code KakaoToken} 엔티티입니다.
     * @return {@code KakaoTokenResponse} Record 인스턴스입니다.
     */
    public static KakaoTokenResponse from(KakaoToken kakaoToken) {
        return new KakaoTokenResponse(
                kakaoToken.getId(),
                kakaoToken.getRunnerId(),
                kakaoToken.getAccessToken(),
                kakaoToken.getRefreshToken()
        );
    }
}