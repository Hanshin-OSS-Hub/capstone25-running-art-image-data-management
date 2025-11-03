package com.aetheriadm.imagemetatdata.infrastructure;

import com.aetheriadm.imagemetatdata.domain.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetadataJpaRepository extends JpaRepository<ImageMetadata, Long> {
}