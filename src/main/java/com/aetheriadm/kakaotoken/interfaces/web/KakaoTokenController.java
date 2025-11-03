package com.aetheriadm.kakaotoken.interfaces.web;

import com.aetheriadm.kakaotoken.application.KakaoTokenService;
import com.aetheriadm.kakaotoken.interfaces.dto.request.KakaoTokenCreateRequest;
import com.aetheriadm.kakaotoken.interfaces.dto.response.KakaoTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kakao-token")
@RequiredArgsConstructor
public class KakaoTokenController {
    private final KakaoTokenService kakaoTokenService;

    @PostMapping
    public ResponseEntity<Void> createKakaoToken(
            Long runnerId, // TODO JWT 토큰에서 정보를 가져오도록 해야함
            @Valid @RequestBody KakaoTokenCreateRequest request
    ) {
        kakaoTokenService.createKakaoToken(runnerId, request.accessToken(), request.refreshToken());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<KakaoTokenResponse> retrieveKakaoToken(Long runnerId) { // TODO JWT 토큰에서 정보를 가져오도록 해야함
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(kakaoTokenService.retrieveKakaoToken(runnerId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteKakaoToken(Long runnerId) { // TODO JWT 토큰에서 정보를 가져오도록 해야함
        kakaoTokenService.deleteKakaoToken(runnerId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
