package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeBirthdays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeBirthdaysRepository extends JpaRepository<EmployeeBirthdays, Long>, JpaSpecificationExecutor<EmployeeBirthdays> {}
