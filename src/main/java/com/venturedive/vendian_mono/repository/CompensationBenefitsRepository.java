package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.CompensationBenefits;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompensationBenefits entity.
 */
@Repository
public interface CompensationBenefitsRepository
    extends JpaRepository<CompensationBenefits, Long>, JpaSpecificationExecutor<CompensationBenefits> {
    default Optional<CompensationBenefits> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CompensationBenefits> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CompensationBenefits> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct compensationBenefits from CompensationBenefits compensationBenefits left join fetch compensationBenefits.benefit",
        countQuery = "select count(distinct compensationBenefits) from CompensationBenefits compensationBenefits"
    )
    Page<CompensationBenefits> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct compensationBenefits from CompensationBenefits compensationBenefits left join fetch compensationBenefits.benefit"
    )
    List<CompensationBenefits> findAllWithToOneRelationships();

    @Query(
        "select compensationBenefits from CompensationBenefits compensationBenefits left join fetch compensationBenefits.benefit where compensationBenefits.id =:id"
    )
    Optional<CompensationBenefits> findOneWithToOneRelationships(@Param("id") Long id);
}
