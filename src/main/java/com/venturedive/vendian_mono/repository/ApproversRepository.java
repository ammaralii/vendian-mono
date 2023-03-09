package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Approvers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Approvers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApproversRepository extends JpaRepository<Approvers, Long>, JpaSpecificationExecutor<Approvers> {}
