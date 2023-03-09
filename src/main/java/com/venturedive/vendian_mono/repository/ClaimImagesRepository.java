package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ClaimImages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClaimImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimImagesRepository extends JpaRepository<ClaimImages, Long>, JpaSpecificationExecutor<ClaimImages> {}
