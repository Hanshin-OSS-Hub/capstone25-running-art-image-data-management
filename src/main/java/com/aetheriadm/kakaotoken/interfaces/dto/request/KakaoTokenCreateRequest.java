package com.aetheriadm.kakaotoken.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;

public record KakaoTokenCreateRequest(
        @NotBlank
        String accessToken,

        @NotBlank
        String refreshToken
) {
}