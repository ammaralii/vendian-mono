package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeComments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeCommentsRepository extends JpaRepository<EmployeeComments, Long>, JpaSpecificationExecutor<EmployeeComments> {}
