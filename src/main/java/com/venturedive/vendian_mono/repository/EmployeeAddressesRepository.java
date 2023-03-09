package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeAddresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeAddressesRepository extends JpaRepository<EmployeeAddresses, Long>, JpaSpecificationExecutor<EmployeeAddresses> {}
