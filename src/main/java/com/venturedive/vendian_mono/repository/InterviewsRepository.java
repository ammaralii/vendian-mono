package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Interviews;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Interviews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewsRepository extends JpaRepository<Interviews, Long>, JpaSpecificationExecutor<Interviews> {}
