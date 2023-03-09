package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserAttributeApprovalRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAttributeApprovalRulesRepository
    extends JpaRepository<UserAttributeApprovalRules, Long>, JpaSpecificationExecutor<UserAttributeApprovalRules> {}
