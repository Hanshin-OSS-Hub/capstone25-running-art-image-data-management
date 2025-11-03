package com.aetheriadm.common.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러가 발생했을 때 HTTP 상태 코드와 메시지를 반환하는 열거형
 *
 * <li>열거형의 접미어로 Exception을 붙이지 않는다.</li>
 * <li>네이밍 일관성을 가지게 한다. NOT_FOUND_XXX, INVALID_XXX, FAILED_XXX, DUPLICATE_XXX 등으로 통일</li>
 *
 * @author duskafka
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorMessage {
    /**
     * [400 BAD_REQUEST]
     * {@code @Valid} 또는 @Validated 실패, 필수 파라미터 누락, 잘못된 메서드 인자 등
     */
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청 파라미터 입니다."),

    /**
     * [400 BAD_REQUEST]
     * JSON 파싱 실패 (e.g., 형식이 맞지 않는 JSON 요청)
     */
    MALFORMED_JSON_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_002", "잘못된 형식의 JSON 요청입니다."),

    /**
     * [500 INTERNAL_SERVER_ERROR]
     * 처리되지 않은 모든 서버 내부 예외
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다.");

    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}