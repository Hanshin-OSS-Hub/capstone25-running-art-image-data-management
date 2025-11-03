package com.aetheriadm.kakaotoken.infrastructure;

import com.aetheriadm.kakaotoken.domain.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 데이터베이스의 상태를 변경하는 작업을 수행하는 레포지토리.
 * 생성, 수정, 삭제 작업에 사용된다.
 */
@Repository
@RequiredArgsConstructor
public class KakaoTokenCommandRepository {
    private final KakaoTokenJpaRepository kakaoTokenJpaRepository;

    public Long createKakaoToken(Long runnerId, String accessToken, String refreshToken) {
        KakaoToken kakaoToken = KakaoToken.builder()
                .runnerId(runnerId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return kakaoTokenJpaRepository.save(kakaoToken).getId();
    }

    public Boolean existById(Long runnerId) {
        return kakaoTokenJpaRepository.existsByRunnerId(runnerId);
    }

    public void update(KakaoToken kakaoToken, String accessToken, String refreshToken) {
        kakaoToken.update(accessToken, refreshToken);
    }

    public void delete(Long kakaoTokenId) {
        kakaoTokenJpaRepository.deleteById(kakaoTokenId);
    }
}