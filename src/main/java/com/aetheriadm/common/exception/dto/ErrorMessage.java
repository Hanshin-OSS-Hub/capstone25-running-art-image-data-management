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
    // SERVER
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청 파라미터입니다."),
    MALFORMED_JSON_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_002", "잘못된 형식의 JSON 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다."),

    // IMAGE_METADATA
    NOT_FOUND_IMAGE_METADATA(HttpStatus.NOT_FOUND, "IMAGE_METADATA_001", "이미지 메타데이터를 찾을 수 없습니다."),
    FORBIDDEN_IMAGE_METADATA(HttpStatus.FORBIDDEN, "IMAGE_METADATA_002", "이미지 메타데이터에 대한 권한이 없습니다."),

    NOT_FOUND_KAKAO_TOKEN(HttpStatus.NOT_FOUND, "KAKAO_TOKEN_001", "카카오 토큰을 찾을 수 없습니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}