package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserRelationApprovalRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRelationApprovalRulesRepository
    extends JpaRepository<UserRelationApprovalRules, Long>, JpaSpecificationExecutor<UserRelationApprovalRules> {}
