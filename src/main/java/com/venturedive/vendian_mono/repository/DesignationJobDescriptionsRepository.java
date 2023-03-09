package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DesignationJobDescriptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationJobDescriptionsRepository
    extends JpaRepository<DesignationJobDescriptions, Long>, JpaSpecificationExecutor<DesignationJobDescriptions> {}
