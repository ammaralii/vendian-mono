package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PcRaterAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcRaterAttributesRepository extends JpaRepository<PcRaterAttributes, Long>, JpaSpecificationExecutor<PcRaterAttributes> {}
