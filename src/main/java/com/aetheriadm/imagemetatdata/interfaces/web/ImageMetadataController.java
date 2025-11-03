package com.aetheriadm.imagemetatdata.interfaces.web;

import com.aetheriadm.imagemetatdata.application.ImageMetadataService;
import com.aetheriadm.imagemetatdata.interfaces.dto.request.ImageMetadataUpdateRequest;
import com.aetheriadm.imagemetatdata.interfaces.dto.response.ImageMetadataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image-metadata")
@RequiredArgsConstructor
public class ImageMetadataController {
    private final ImageMetadataService imageMetadataService;

    @GetMapping("/{imageMetadataId}")
    public ResponseEntity<ImageMetadataResponse> retrieveImageMetadata(
            Long runnerId, // TODO Spring Security JWT 로 인증 정보를 추출하여 가져오도록 구현해야함
            @PathVariable Long imageMetadataId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageMetadataService.retrieveImageMetadata(runnerId, imageMetadataId));
    }

    @GetMapping("/runner")
    public ResponseEntity<List<ImageMetadataResponse>> retrieveAllImageMetadataByRunnerId(
            Long runnerId // TODO Spring Security JWT 로 인증 정보를 추출하여 가져오도록 구현해야함
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageMetadataService.retrieveAllImageMetadataByRunnerId(runnerId));
    }

    @PatchMapping("/{imageMetadataId}")
    public ResponseEntity<Void> updateImageMetadata(
            Long runnerId, // TODO Spring Security JWT 로 인증 정보를 추출하여 가져오도록 구현해야함
            @PathVariable Long imageMetadataId,
            @Valid @RequestBody ImageMetadataUpdateRequest request
    ) {
        imageMetadataService.updateImageMetadata(runnerId, imageMetadataId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{imageMetadataId}")
    public ResponseEntity<Void> deleteImageMetadata(
            Long runnerId, // TODO Spring Security JWT 로 인증 정보를 추출하여 가져오도록 구현해야함
            @PathVariable Long imageMetadataId
    ) {
        imageMetadataService.deleteImageMetadata(runnerId, imageMetadataId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}