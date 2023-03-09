package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Configurations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Configurations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationsRepository extends JpaRepository<Configurations, Long>, JpaSpecificationExecutor<Configurations> {}
