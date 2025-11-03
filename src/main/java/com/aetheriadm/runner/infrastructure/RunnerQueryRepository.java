package com.aetheriadm.runner.infrastructure;

import com.aetheriadm.runner.domain.Runner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 데이터베이스의 상태를 변경하지 않고, 데이터를 조회하는 작업을 하는 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class RunnerQueryRepository {
    private final RunnerJpaRepository runnerJpaRepository;

    public Boolean existsByKakaoId(Long kakaoId) {
        return runnerJpaRepository.existByKakaoId(kakaoId);
    }

    public Optional<Runner> retrieveById(Long runnerId) {
        return runnerJpaRepository.findById(runnerId);
    }
}