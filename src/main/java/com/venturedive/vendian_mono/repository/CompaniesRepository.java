package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Companies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Companies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Long>, JpaSpecificationExecutor<Companies> {}
