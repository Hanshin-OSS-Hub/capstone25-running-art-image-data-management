package com.aetheriadm.kakaotoken.infrastructure;

import com.aetheriadm.kakaotoken.domain.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoTokenJpaRepository extends JpaRepository<KakaoToken, Long> {
    Boolean existsByRunnerId(Long runnerId);
    Optional<KakaoToken> findByRunnerId(Long runnerId);
}