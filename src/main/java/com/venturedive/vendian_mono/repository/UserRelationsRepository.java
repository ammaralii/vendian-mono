package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserRelations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserRelations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRelationsRepository extends JpaRepository<UserRelations, Long>, JpaSpecificationExecutor<UserRelations> {}
