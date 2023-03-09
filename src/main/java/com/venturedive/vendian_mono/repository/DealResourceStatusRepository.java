package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealResourceStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealResourceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealResourceStatusRepository
    extends JpaRepository<DealResourceStatus, Long>, JpaSpecificationExecutor<DealResourceStatus> {}
