package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Documents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Documents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>, JpaSpecificationExecutor<Documents> {}
