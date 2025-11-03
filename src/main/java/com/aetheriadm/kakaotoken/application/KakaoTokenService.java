package com.aetheriadm.kakaotoken.application;

import com.aetheriadm.common.exception.BusinessException;
import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.kakaotoken.domain.KakaoToken;
import com.aetheriadm.kakaotoken.infrastructure.KakaoTokenCommandRepository;
import com.aetheriadm.kakaotoken.infrastructure.KakaoTokenQueryRepository;
import com.aetheriadm.kakaotoken.interfaces.dto.response.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoTokenService {
    private final KakaoTokenCommandRepository kakaoTokenCommandRepository;
    private final KakaoTokenQueryRepository kakaoTokenQueryRepository;

    @Transactional
    public Long createKakaoToken(Long runnerId, String accessToken, String refreshToken) {
        if (kakaoTokenQueryRepository.existById(runnerId)) {
            KakaoToken kakaoToken = kakaoTokenQueryRepository.retrieveByRunnerId(runnerId).orElseThrow(() ->
                    new BusinessException(
                            ErrorMessage.NOT_FOUND_KAKAO_TOKEN,
                            String.format("사용자(%d)의 토큰을 찾지 못했습니다.", runnerId)
                    )
            );
            kakaoTokenCommandRepository.update(kakaoToken, accessToken, refreshToken);
        }

        return kakaoTokenCommandRepository.createKakaoToken(runnerId, accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public KakaoTokenResponse retrieveKakaoToken(Long runnerId) {
        KakaoToken kakaoToken = kakaoTokenQueryRepository.retrieveByRunnerId(runnerId).orElseThrow(() ->
                new BusinessException(
                        ErrorMessage.NOT_FOUND_KAKAO_TOKEN,
                        String.format("사용자(%d)의 토큰을 찾지 못했습니다.", runnerId)
                )
        );
        return KakaoTokenResponse.from(kakaoToken);
    }

    @Transactional
    public void deleteKakaoToken(Long runnerId) {
        KakaoToken kakaoToken = kakaoTokenQueryRepository.retrieveByRunnerId(runnerId).orElseThrow(() ->
                new BusinessException(
                        ErrorMessage.NOT_FOUND_KAKAO_TOKEN,
                        String.format("사용자(%d)의 토큰을 찾지 못했습니다.", runnerId)
                )
        );
        kakaoTokenCommandRepository.delete(kakaoToken.getId());
    }
}