package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.MaritalStatuses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaritalStatuses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaritalStatusesRepository extends JpaRepository<MaritalStatuses, Long>, JpaSpecificationExecutor<MaritalStatuses> {}
