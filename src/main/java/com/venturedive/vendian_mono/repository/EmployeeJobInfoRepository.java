package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeJobInfo entity.
 */
@Repository
public interface EmployeeJobInfoRepository extends JpaRepository<EmployeeJobInfo, Long>, JpaSpecificationExecutor<EmployeeJobInfo> {
    default Optional<EmployeeJobInfo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EmployeeJobInfo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EmployeeJobInfo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct employeeJobInfo from EmployeeJobInfo employeeJobInfo left join fetch employeeJobInfo.employmenttype",
        countQuery = "select count(distinct employeeJobInfo) from EmployeeJobInfo employeeJobInfo"
    )
    Page<EmployeeJobInfo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct employeeJobInfo from EmployeeJobInfo employeeJobInfo left join fetch employeeJobInfo.employmenttype")
    List<EmployeeJobInfo> findAllWithToOneRelationships();

    @Query(
        "select employeeJobInfo from EmployeeJobInfo employeeJobInfo left join fetch employeeJobInfo.employmenttype where employeeJobInfo.id =:id"
    )
    Optional<EmployeeJobInfo> findOneWithToOneRelationships(@Param("id") Long id);
}
