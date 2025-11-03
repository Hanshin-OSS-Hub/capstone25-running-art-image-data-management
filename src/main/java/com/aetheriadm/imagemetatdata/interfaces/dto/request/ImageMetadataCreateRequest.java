package com.aetheriadm.imagemetatdata.interfaces.dto.request;

import com.aetheriadm.imagemetatdata.domain.vo.Proficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageMetadataCreateRequest(
        @NotBlank
        String location,

        @NotNull
        Proficiency proficiency,

        @NotBlank
        String shape
) {
}
