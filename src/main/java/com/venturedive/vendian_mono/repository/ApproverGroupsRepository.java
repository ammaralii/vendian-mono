package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ApproverGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApproverGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApproverGroupsRepository extends JpaRepository<ApproverGroups, Long>, JpaSpecificationExecutor<ApproverGroups> {}
