package com.aetheriadm.runner.infrastructure;

import com.aetheriadm.runner.domain.Runner;
import com.aetheriadm.runner.interfaces.dto.request.RunnerCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 데이터베이스의 상태를 변경하는 작업을 수행하는 레포지토리.
 * 생성, 수정, 삭제 작업에 사용된다.
 */
@Repository
@RequiredArgsConstructor
public class RunnerCommandRepository {
    private final RunnerJpaRepository runnerJpaRepository;

    public Long save(RunnerCreateRequest request) {
        Runner runner = Runner.builder()
                .kakaoId(request.kakaoId())
                .name(request.name())
                .build();
        return runnerJpaRepository.save(runner).getId();
    }


    public void deleteById(Long runnerId) {
        runnerJpaRepository.deleteById(runnerId);
    }

    public void deleteByKakaoId(Long kakaoId) {
        runnerJpaRepository.deleteByKakaoId(kakaoId);
    }
}