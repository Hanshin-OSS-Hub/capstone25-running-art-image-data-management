package com.aetheriadm.imagemetatdata.infrastructure;

import com.aetheriadm.common.exception.BusinessException;
import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataCreateRequest;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * 데이터베이스의 상태를 변경하는 작업을 수행하는 레포지토리.
 * 생성, 수정, 삭제 작업에 사용된다.
 */
@Repository
@RequiredArgsConstructor
public class ImageMetadataCommandRepository {
    private final ImageMetadataJpaRepository imageMetadataJpaRepository;

    @Transactional
    public Long save(Long runnerId, ImageMetadataCreateRequest request) {
        ImageMetadata entity = ImageMetadata.toEntity(
                runnerId,
                runnerId + "-" + UUID.randomUUID(),
                request.location(),
                request.shape(),
                request.proficiency()
        );

        return imageMetadataJpaRepository.save(entity).getId();
    }

    @Transactional
    public void update(Long runnerId, Long imageMetadataId, ImageMetadataUpdateRequest request) {
        imageMetadataJpaRepository.findById(imageMetadataId)
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                                String.format("요청한 이미지 메타데이터(%d)를 찾지 못했습니다.", imageMetadataId)
                        )
                )
                .update(request.title(), request.description());
    }

    @Transactional
    public void delete(Long runnerId, Long imageMetadataId) {
        ImageMetadata imageMetadata = imageMetadataJpaRepository.findById(imageMetadataId)
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                                String.format("요청한 이미지 메타데이터(%d)를 찾지 못했습니다.", imageMetadataId)
                        )
                );

        if(!Objects.equals(imageMetadata.getRunnerId(), runnerId)) {
            throw new BusinessException(
                    ErrorMessage.FORBIDDEN_IMAGE_METADATA,
                    String.format("요청한 이미지 메타데이터(%d)에 대한 소유권이 없습니다.", imageMetadataId)
            );
        }

        imageMetadataJpaRepository.delete(imageMetadata);
    }
}