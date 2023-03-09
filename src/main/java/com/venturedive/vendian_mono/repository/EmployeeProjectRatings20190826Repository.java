package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeProjectRatings20190826 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProjectRatings20190826Repository
    extends JpaRepository<EmployeeProjectRatings20190826, Long>, JpaSpecificationExecutor<EmployeeProjectRatings20190826> {}
