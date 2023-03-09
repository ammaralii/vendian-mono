package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeavesCopy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeavesCopy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeavesCopyRepository extends JpaRepository<LeavesCopy, Long>, JpaSpecificationExecutor<LeavesCopy> {}
