package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.QualificationTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QualificationTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualificationTypesRepository
    extends JpaRepository<QualificationTypes, Long>, JpaSpecificationExecutor<QualificationTypes> {}
