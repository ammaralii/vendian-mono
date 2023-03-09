package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserAttributeEscalationRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAttributeEscalationRulesRepository
    extends JpaRepository<UserAttributeEscalationRules, Long>, JpaSpecificationExecutor<UserAttributeEscalationRules> {}
