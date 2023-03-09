package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.WorkLogDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkLogDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkLogDetailsRepository extends JpaRepository<WorkLogDetails, Long>, JpaSpecificationExecutor<WorkLogDetails> {}
