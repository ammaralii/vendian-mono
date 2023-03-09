package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveDetailAdjustmentLogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveDetailAdjustmentLogsRepository
    extends JpaRepository<LeaveDetailAdjustmentLogs, Long>, JpaSpecificationExecutor<LeaveDetailAdjustmentLogs> {}
