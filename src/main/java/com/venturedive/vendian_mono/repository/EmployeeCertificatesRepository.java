package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeCertificates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeCertificatesRepository
    extends JpaRepository<EmployeeCertificates, Long>, JpaSpecificationExecutor<EmployeeCertificates> {}
