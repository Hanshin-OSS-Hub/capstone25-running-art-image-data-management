package com.aetheriadm.runner.application;

import com.aetheriadm.common.exception.BusinessException;
import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.runner.domain.Runner;
import com.aetheriadm.runner.infrastructure.RunnerCommandRepository;
import com.aetheriadm.runner.infrastructure.RunnerQueryRepository;
import com.aetheriadm.runner.interfaces.dto.request.RunnerCreateRequest;
import com.aetheriadm.runner.interfaces.dto.response.RunnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RunnerService {
    private final RunnerCommandRepository runnerCommandRepository;
    private final RunnerQueryRepository runnerQueryRepository;


    @Transactional
    public Long createRunner(RunnerCreateRequest request) {
        if (runnerQueryRepository.existByKakaoId(request.kakaoId())) {
            throw new BusinessException(ErrorMessage.DUPLICATE_KAKAO_ID);
        }
        return runnerCommandRepository.save(request);
    }

    @Transactional(readOnly = true)
    public RunnerResponse retrieveRunner(Long runnerId) {
        Runner runner = runnerQueryRepository.retrieveById(runnerId)
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_RUNNER,
                                String.format("요청한 러너(%d)를 찾지 못했습니다.", runnerId)
                        )
                );
        return RunnerResponse.from(runner);
    }

    @Transactional
    public void deleteRunner(Long runnerId) {
        Runner runner = runnerQueryRepository.retrieveById(runnerId)
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_RUNNER,
                                String.format("요청한 러너(%d)를 찾지 못했습니다.", runnerId)
                        )
                );
        if (!Objects.equals(runner.getId(), runnerId)) {
            throw new BusinessException(
                    ErrorMessage.FORBIDDEN_RUNNER,
                    String.format("요청한 러너(%d)가 아니라 삭제할 수 없습니다.", runnerId)
            );
        }

        runnerCommandRepository.deleteById(runnerId);
    }
}