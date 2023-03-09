package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ApproverFlows;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApproverFlows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApproverFlowsRepository extends JpaRepository<ApproverFlows, Long>, JpaSpecificationExecutor<ApproverFlows> {}
