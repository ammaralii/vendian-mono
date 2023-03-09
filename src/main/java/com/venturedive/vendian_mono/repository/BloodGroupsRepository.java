package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.BloodGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BloodGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodGroupsRepository extends JpaRepository<BloodGroups, Long>, JpaSpecificationExecutor<BloodGroups> {}
