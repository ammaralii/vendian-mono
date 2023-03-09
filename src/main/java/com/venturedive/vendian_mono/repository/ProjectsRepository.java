package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Projects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long>, JpaSpecificationExecutor<Projects> {}
