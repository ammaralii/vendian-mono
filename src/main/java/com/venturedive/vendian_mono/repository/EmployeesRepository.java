package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Employees;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employees entity.
 */
@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long>, JpaSpecificationExecutor<Employees> {
    default Optional<Employees> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Employees> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Employees> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct employees from Employees employees left join fetch employees.employmentstatus left join fetch employees.employmenttype",
        countQuery = "select count(distinct employees) from Employees employees"
    )
    Page<Employees> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct employees from Employees employees left join fetch employees.employmentstatus left join fetch employees.employmenttype"
    )
    List<Employees> findAllWithToOneRelationships();

    @Query(
        "select employees from Employees employees left join fetch employees.employmentstatus left join fetch employees.employmenttype where employees.id =:id"
    )
    Optional<Employees> findOneWithToOneRelationships(@Param("id") Long id);
}
