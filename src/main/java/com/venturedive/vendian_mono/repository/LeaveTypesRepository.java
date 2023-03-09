package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeaveTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveTypesRepository extends JpaRepository<LeaveTypes, Long>, JpaSpecificationExecutor<LeaveTypes> {}
