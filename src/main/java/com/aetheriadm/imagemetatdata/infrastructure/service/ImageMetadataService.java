package com.aetheriadm.imagemetatdata.infrastructure.service;

import com.aetheriadm.common.exception.BusinessException;
import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import com.aetheriadm.imagemetatdata.infrastructure.repository.ImageMetadataCommandRepository;
import com.aetheriadm.imagemetatdata.infrastructure.repository.ImageMetadataQueryRepository;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataCreateRequest;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataUpdateRequest;
import com.aetheriadm.imagemetatdata.interfaces.dto.response.ImageMetadataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageMetadataService {
    private final ImageMetadataCommandRepository imageMetadataCommandRepository;
    private final ImageMetadataQueryRepository imageMetadataQueryRepository;

    public Long createImageMetadata(Long runnerId, ImageMetadataCreateRequest request) {
        return imageMetadataCommandRepository.save(runnerId, request);
    }

    @Transactional
    public void updateImageMetadata(Long runnerId, Long imageMetadataId, ImageMetadataUpdateRequest request) {
        ImageMetadata imageMetadata = Optional.ofNullable(imageMetadataQueryRepository.retrieveById(imageMetadataId))
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                                String.format("요청한 이미지 메타데이터(%d)를 찾지 못했습니다.", imageMetadataId)
                        )
                );

        if (!Objects.equals(imageMetadata.getRunnerId(), runnerId)) {
            throw new BusinessException(
                    ErrorMessage.FORBIDDEN_IMAGE_METADATA,
                    String.format("요청한 이미지 메타데이터(%d)에 대한 소유권이 없습니다.", imageMetadataId)
            );
        }

        imageMetadataCommandRepository.update(imageMetadata, request);
    }

    @Transactional
    public void deleteImageMetadata(Long runnerId, Long imageMetadataId) {
        ImageMetadata imageMetadata = Optional.ofNullable(imageMetadataQueryRepository.retrieveById(imageMetadataId))
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                                String.format("요청한 이미지 메타데이터(%d)를 찾지 못했습니다.", imageMetadataId)
                        )
                );

        if (!Objects.equals(imageMetadata.getRunnerId(), runnerId)) {
            throw new BusinessException(
                    ErrorMessage.FORBIDDEN_IMAGE_METADATA,
                    String.format("요청한 이미지 메타데이터(%d)에 대한 소유권이 없습니다.", imageMetadataId)
            );
        }

        imageMetadataCommandRepository.delete(imageMetadataId);
    }

    public ImageMetadataResponse retrieveImageMetadata(Long imageMetadataId) {
        ImageMetadata imageMetadata = Optional.ofNullable(imageMetadataQueryRepository.retrieveById(imageMetadataId))
                .orElseThrow(() -> new BusinessException(
                                ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                                String.format("요청한 이미지 메타데이터(%d)를 찾지 못했습니다.", imageMetadataId)
                        )
                );
        return ImageMetadataResponse.from(imageMetadata);
    }

    public List<ImageMetadataResponse> retrieveAllImageMetadataByRunnerId(Long runnerId) {
        List<ImageMetadata> imageMetadata = imageMetadataQueryRepository.retrieveAllByRunnerId(runnerId);
        if (imageMetadata.isEmpty()) {
            throw new BusinessException(
                    ErrorMessage.NOT_FOUND_IMAGE_METADATA,
                    String.format("사용자(%d)의 이미지 메타데이터를 찾지 못했습니다.", runnerId)
            );
        }

        return imageMetadata.stream()
                .map(ImageMetadataResponse::from)
                .toList();
    }
}