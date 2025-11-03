package com.aetheriadm.kakaotoken.infrastructure;

import com.aetheriadm.kakaotoken.domain.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 데이터베이스의 상태를 변경하지 않고, 데이터를 조회하는 작업을 하는 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class KakaoTokenQueryRepository {
    private final KakaoTokenJpaRepository kakaoTokenJpaRepository;

    public Optional<KakaoToken> retrieveByRunnerId(Long runnerId) {
        return kakaoTokenJpaRepository.findByRunnerId(runnerId);
    }

    public Boolean existsByRunnerId(Long runnerId) {
        return kakaoTokenJpaRepository.existsByRunnerId(runnerId);
    }
}