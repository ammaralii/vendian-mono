package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectCycles20190826;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectCycles20190826 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCycles20190826Repository
    extends JpaRepository<ProjectCycles20190826, Long>, JpaSpecificationExecutor<ProjectCycles20190826> {}
