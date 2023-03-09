package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealRequirementMatchingResources entity.
 */
@Repository
public interface DealRequirementMatchingResourcesRepository
    extends JpaRepository<DealRequirementMatchingResources, Long>, JpaSpecificationExecutor<DealRequirementMatchingResources> {
    default Optional<DealRequirementMatchingResources> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DealRequirementMatchingResources> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DealRequirementMatchingResources> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dealRequirementMatchingResources from DealRequirementMatchingResources dealRequirementMatchingResources left join fetch dealRequirementMatchingResources.resourcestatus left join fetch dealRequirementMatchingResources.systemstatus",
        countQuery = "select count(distinct dealRequirementMatchingResources) from DealRequirementMatchingResources dealRequirementMatchingResources"
    )
    Page<DealRequirementMatchingResources> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dealRequirementMatchingResources from DealRequirementMatchingResources dealRequirementMatchingResources left join fetch dealRequirementMatchingResources.resourcestatus left join fetch dealRequirementMatchingResources.systemstatus"
    )
    List<DealRequirementMatchingResources> findAllWithToOneRelationships();

    @Query(
        "select dealRequirementMatchingResources from DealRequirementMatchingResources dealRequirementMatchingResources left join fetch dealRequirementMatchingResources.resourcestatus left join fetch dealRequirementMatchingResources.systemstatus where dealRequirementMatchingResources.id =:id"
    )
    Optional<DealRequirementMatchingResources> findOneWithToOneRelationships(@Param("id") Long id);
}
