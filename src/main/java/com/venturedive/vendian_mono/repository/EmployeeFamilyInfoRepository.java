package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeFamilyInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeFamilyInfoRepository
    extends JpaRepository<EmployeeFamilyInfo, Long>, JpaSpecificationExecutor<EmployeeFamilyInfo> {}
