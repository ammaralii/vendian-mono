package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PerformanceCycles20190826;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceCycles20190826 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceCycles20190826Repository
    extends JpaRepository<PerformanceCycles20190826, Long>, JpaSpecificationExecutor<PerformanceCycles20190826> {}
