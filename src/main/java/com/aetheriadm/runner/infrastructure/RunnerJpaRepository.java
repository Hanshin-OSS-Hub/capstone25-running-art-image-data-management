package com.aetheriadm.runner.infrastructure;

import com.aetheriadm.runner.domain.Runner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunnerJpaRepository extends JpaRepository<Runner, Long> {
    Boolean existsByKakaoId(Long kakaoId);
    void deleteByKakaoId(Long kakaoId);
}