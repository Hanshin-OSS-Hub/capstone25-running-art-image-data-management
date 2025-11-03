package com.aetheriadm.imagemetatdata.infrastructure.repository;

import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataCreateRequest;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 데이터베이스의 상태를 변경하는 작업을 수행하는 레포지토리.
 * 생성, 수정, 삭제 작업에 사용된다.
 */
@Repository
@RequiredArgsConstructor
public class ImageMetadataCommandRepository {
    private final ImageMetadataJpaRepository imageMetadataJpaRepository;

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

    public void update(ImageMetadata imageMetadata,  ImageMetadataUpdateRequest request) {
       imageMetadata.update(request.title(), request.description());
    }

    public void delete(Long imageMetadataId) {
        imageMetadataJpaRepository.deleteById(imageMetadataId);
    }
}