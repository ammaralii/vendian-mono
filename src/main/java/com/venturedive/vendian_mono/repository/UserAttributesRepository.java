package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserAttributes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAttributesRepository extends JpaRepository<UserAttributes, Long>, JpaSpecificationExecutor<UserAttributes> {}
