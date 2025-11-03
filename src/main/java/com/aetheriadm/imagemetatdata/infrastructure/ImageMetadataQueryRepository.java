package com.aetheriadm.imagemetatdata.infrastructure;

import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 데이터베이스의 상태를 변경하지 않고, 데이터를 조회하는 작업을 하는 레포지토리
 * */
@Repository
@RequiredArgsConstructor
public class ImageMetadataQueryRepository {
    private final ImageMetadataJpaRepository imageMetadataJpaRepository;

    @Transactional(readOnly = true)
    public ImageMetadata retrieveById(Long imageMetadataId) {
        return imageMetadataJpaRepository.findById(imageMetadataId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ImageMetadata> retrieveAllByRunnerId(Long runnerId) {
        return imageMetadataJpaRepository.findAll().stream()
                .toList();
    }
}