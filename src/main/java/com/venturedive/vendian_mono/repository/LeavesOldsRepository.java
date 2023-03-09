package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.LeavesOlds;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeavesOlds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeavesOldsRepository extends JpaRepository<LeavesOlds, Long>, JpaSpecificationExecutor<LeavesOlds> {}
