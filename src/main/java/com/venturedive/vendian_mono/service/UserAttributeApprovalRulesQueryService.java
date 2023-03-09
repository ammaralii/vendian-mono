package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.repository.UserAttributeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributeApprovalRulesCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link UserAttributeApprovalRules} entities in the database.
 * The main input is a {@link UserAttributeApprovalRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAttributeApprovalRules} or a {@link Page} of {@link UserAttributeApprovalRules} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAttributeApprovalRulesQueryService extends QueryService<UserAttributeApprovalRules> {

    private final Logger log = LoggerFactory.getLogger(UserAttributeApprovalRulesQueryService.class);

    private final UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository;

    public UserAttributeApprovalRulesQueryService(UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository) {
        this.userAttributeApprovalRulesRepository = userAttributeApprovalRulesRepository;
    }

    /**
     * Return a {@link List} of {@link UserAttributeApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAttributeApprovalRules> findByCriteria(UserAttributeApprovalRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAttributeApprovalRules> specification = createSpecification(criteria);
        return userAttributeApprovalRulesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserAttributeApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributeApprovalRules> findByCriteria(UserAttributeApprovalRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAttributeApprovalRules> specification = createSpecification(criteria);
        return userAttributeApprovalRulesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAttributeApprovalRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAttributeApprovalRules> specification = createSpecification(criteria);
        return userAttributeApprovalRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAttributeApprovalRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAttributeApprovalRules> createSpecification(UserAttributeApprovalRulesCriteria criteria) {
        Specification<UserAttributeApprovalRules> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAttributeApprovalRules_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), UserAttributeApprovalRules_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserAttributeApprovalRules_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserAttributeApprovalRules_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserAttributeApprovalRules_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserAttributeApprovalRules_.version));
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(UserAttributeApprovalRules_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getLeaveApprovalCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveApprovalCriteriaId(),
                            root ->
                                root.join(UserAttributeApprovalRules_.leaveApprovalCriteria, JoinType.LEFT).get(LeaveApprovalCriterias_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
