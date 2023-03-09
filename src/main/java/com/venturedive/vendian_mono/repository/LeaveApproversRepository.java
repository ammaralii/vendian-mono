package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveApprovers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveApprovers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApproversRepository extends JpaRepository<LeaveApprovers, Long>, JpaSpecificationExecutor<LeaveApprovers> {}
