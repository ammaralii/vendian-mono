package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeDocumentsRepository extends JpaRepository<EmployeeDocuments, Long>, JpaSpecificationExecutor<EmployeeDocuments> {}
