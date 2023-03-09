package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeTalents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeTalents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTalentsRepository extends JpaRepository<EmployeeTalents, Long>, JpaSpecificationExecutor<EmployeeTalents> {}
