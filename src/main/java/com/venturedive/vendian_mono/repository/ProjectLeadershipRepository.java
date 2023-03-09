package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectLeadership;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectLeadership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectLeadershipRepository extends JpaRepository<ProjectLeadership, Long>, JpaSpecificationExecutor<ProjectLeadership> {}
