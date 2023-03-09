package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PerformanceCycles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceCycles entity.
 *
 * When extending this class, extend PerformanceCyclesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PerformanceCyclesRepository
    extends
        PerformanceCyclesRepositoryWithBagRelationships,
        JpaRepository<PerformanceCycles, Long>,
        JpaSpecificationExecutor<PerformanceCycles> {
    default Optional<PerformanceCycles> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PerformanceCycles> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PerformanceCycles> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
