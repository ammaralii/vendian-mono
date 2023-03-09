package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectCycles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProjectCycles entity.
 *
 * When extending this class, extend ProjectCyclesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ProjectCyclesRepository
    extends ProjectCyclesRepositoryWithBagRelationships, JpaRepository<ProjectCycles, Long>, JpaSpecificationExecutor<ProjectCycles> {
    default Optional<ProjectCycles> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ProjectCycles> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ProjectCycles> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
