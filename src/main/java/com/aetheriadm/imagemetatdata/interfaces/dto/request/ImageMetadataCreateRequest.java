package com.aetheriadm.imagemetatdata.interfaces.dto.request;

import com.aetheriadm.imagemetatdata.domain.vo.Proficiency;

public record ImageMetadataCreateRequest(
        String location,
        Proficiency proficiency,
        String shape
) {
}
