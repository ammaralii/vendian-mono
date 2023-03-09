package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long>, JpaSpecificationExecutor<LeaveDetails> {}
