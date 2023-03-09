package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveRequestApprovers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRequestApproversRepository
    extends JpaRepository<LeaveRequestApprovers, Long>, JpaSpecificationExecutor<LeaveRequestApprovers> {}
