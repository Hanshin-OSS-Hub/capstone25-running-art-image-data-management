package com.aetheriadm.kakaotoken.interfaces.dto.request;

public record KakaoTokenCreateRequest(
        String accessToken,
        String refreshToken
) {
}