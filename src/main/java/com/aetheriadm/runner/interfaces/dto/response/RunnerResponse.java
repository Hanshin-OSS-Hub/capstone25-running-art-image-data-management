package com.aetheriadm.runner.interfaces.dto.response;

import com.aetheriadm.runner.domain.Runner;

/**
 * 사용자({@code Runner}) 정보 조회 요청에 대한 응답 DTO를 Java Record로 정의했습니다.
 *
 * <p>사용자의 고유 ID와 닉네임 등 클라이언트에게 노출해도 되는 정보를 전달합니다.</p>
 *
 * @param id        사용자의 고유 식별자 (Primary Key)입니다.
 * @param kakaoId   카카오로부터 발급받은 사용자의 고유 식별 ID입니다.
 * @param name      사용자의 이름 또는 닉네임입니다.
 */
public record RunnerResponse(
        Long id,
        Long kakaoId,
        String name
) {
    /**
     * {@code Runner} 엔티티를 {@code RunnerResponse} Record로 변환하는 정적 팩토리 메서드입니다.
     *
     * @param runner 변환할 {@code Runner} 엔티티입니다.
     * @return {@code RunnerResponse} Record 인스턴스입니다.
     */
    public static RunnerResponse from(Runner runner) {
        return new RunnerResponse(
                runner.getId(),
                runner.getKakaoId(),
                runner.getName()
        );
    }
}