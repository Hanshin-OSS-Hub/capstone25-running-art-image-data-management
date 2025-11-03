package com.aetheriadm.runner.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 새로운 사용자를 생성하기 위한 요청 DTO (Data Transfer Object)입니다.
 *
 * <p>주로 카카오 로그인 후 신규 사용자 정보를 서버에 등록할 때 사용되는
 * 입력 데이터를 정의하며, 불변성을 보장하는 Java Record로 구현되었습니다.</p>
 *
 * @param kakaoId 카카오로부터 발급받은 사용자의 고유 식별 ID입니다.
 * @param name 사용자가 사용할 이름 또는 닉네임입니다.
 */
public record RunnerCreateRequest(
        @NotNull
        Long kakaoId,

        @NotBlank
        String name
) {
}