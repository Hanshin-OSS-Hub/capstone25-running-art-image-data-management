package com.aetheriadm.imagemetatdata.interfaces.dto.response;

import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import com.aetheriadm.imagemetatdata.domain.vo.Proficiency;

import java.time.LocalDate;

/**
 * 이미지 메타데이터 조회 요청에 대한 응답 DTO를 Java Record로 정의했습니다.
 *
 * <p>Record는 불변성, 생성자, 접근자(Getter), equals(), hashCode(), toString()을 자동으로 제공합니다.</p>
 *
 * @param id             이미지 메타데이터의 고유 식별자 (Primary Key)입니다.
 * @param runnerId       이미지를 생성한 사용자의 고유 식별자입니다.
 * @param imagePath      이미지 파일이 저장된 스토리지 상의 경로입니다.
 * @param title          사용자가 지정한 이미지의 제목입니다.
 * @param description    사용자가 지정한 이미지에 대한 설명입니다.
 * @param location       이미지가 생성된 위치(지역) 정보입니다.
 * @param shape          러닝 아트 이미지의 형태입니다.
 * @param proficiency    러닝 아트 이미지 생성 시 달성한 난이도 또는 숙련도 레벨입니다.
 * @param shared         이미지가 공개적으로 공유되었는지 여부입니다.
 * @param createdAt      메타데이터가 데이터베이스에 처음 생성된 날짜입니다.
 * @param modifiedAt     메타데이터가 마지막으로 수정된 날짜입니다.
 */
public record ImageMetadataResponse(
        Long id,
        Long runnerId,
        String imagePath,
        String title,
        String description,
        String location,
        String shape,
        Proficiency proficiency,
        Boolean shared,
        LocalDate createdAt,
        LocalDate modifiedAt
) {
    /**
     * {@code ImageMetadata} 엔티티를 {@code ImageMetadataResponse} Record로 변환하는 정적 팩토리 메서드입니다.
     *
     * @param metadata 변환할 {@code ImageMetadata} 엔티티입니다.
     * @return {@code ImageMetadataResponse} Record 인스턴스입니다.
     */
    public static ImageMetadataResponse from(ImageMetadata metadata) {
        return new ImageMetadataResponse(
                metadata.getId(),
                metadata.getRunnerId(),
                metadata.getImagePath(),
                metadata.getTitle(),
                metadata.getDescription(),
                metadata.getLocation(),
                metadata.getShape(),
                metadata.getProficiency(),
                metadata.getShared(),
                metadata.getCreatedAt(),
                metadata.getModifiedAt()
        );
    }
}