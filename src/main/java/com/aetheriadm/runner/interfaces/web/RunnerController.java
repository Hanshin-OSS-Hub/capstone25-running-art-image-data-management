package com.aetheriadm.runner.interfaces.web;

import com.aetheriadm.runner.application.RunnerService;
import com.aetheriadm.runner.interfaces.dto.request.RunnerCreateRequest;
import com.aetheriadm.runner.interfaces.dto.response.RunnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/runner")
@RequiredArgsConstructor
public class RunnerController {
    private final RunnerService runnerService;

    @PostMapping
    public ResponseEntity<Long> createRunner(@RequestBody RunnerCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(runnerService.createRunner(request));
    }

    @GetMapping
    public ResponseEntity<RunnerResponse> retrieveRunner(Long runnerId) { // TODO JWT 인증 기능 구현
        RunnerResponse runnerResponse = runnerService.retrieveRunner(runnerId);
        return ResponseEntity.status(HttpStatus.OK).body(runnerResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRunner(Long runnerId) { // TODO JWT 인증 기능 구현
        runnerService.deleteRunner(runnerId);
        return ResponseEntity.noContent().build();
    }
}