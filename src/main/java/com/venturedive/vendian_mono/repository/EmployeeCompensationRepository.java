package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeCompensation entity.
 */
@Repository
public interface EmployeeCompensationRepository
    extends JpaRepository<EmployeeCompensation, Long>, JpaSpecificationExecutor<EmployeeCompensation> {
    default Optional<EmployeeCompensation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EmployeeCompensation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EmployeeCompensation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct employeeCompensation from EmployeeCompensation employeeCompensation left join fetch employeeCompensation.reason",
        countQuery = "select count(distinct employeeCompensation) from EmployeeCompensation employeeCompensation"
    )
    Page<EmployeeCompensation> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct employeeCompensation from EmployeeCompensation employeeCompensation left join fetch employeeCompensation.reason"
    )
    List<EmployeeCompensation> findAllWithToOneRelationships();

    @Query(
        "select employeeCompensation from EmployeeCompensation employeeCompensation left join fetch employeeCompensation.reason where employeeCompensation.id =:id"
    )
    Optional<EmployeeCompensation> findOneWithToOneRelationships(@Param("id") Long id);
}
