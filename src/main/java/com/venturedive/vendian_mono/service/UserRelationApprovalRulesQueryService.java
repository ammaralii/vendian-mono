package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.repository.UserRelationApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserRelationApprovalRulesCriteria;
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
 * Service for executing complex queries for {@link UserRelationApprovalRules} entities in the database.
 * The main input is a {@link UserRelationApprovalRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRelationApprovalRules} or a {@link Page} of {@link UserRelationApprovalRules} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRelationApprovalRulesQueryService extends QueryService<UserRelationApprovalRules> {

    private final Logger log = LoggerFactory.getLogger(UserRelationApprovalRulesQueryService.class);

    private final UserRelationApprovalRulesRepository userRelationApprovalRulesRepository;

    public UserRelationApprovalRulesQueryService(UserRelationApprovalRulesRepository userRelationApprovalRulesRepository) {
        this.userRelationApprovalRulesRepository = userRelationApprovalRulesRepository;
    }

    /**
     * Return a {@link List} of {@link UserRelationApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRelationApprovalRules> findByCriteria(UserRelationApprovalRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRelationApprovalRules> specification = createSpecification(criteria);
        return userRelationApprovalRulesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserRelationApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRelationApprovalRules> findByCriteria(UserRelationApprovalRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRelationApprovalRules> specification = createSpecification(criteria);
        return userRelationApprovalRulesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRelationApprovalRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRelationApprovalRules> specification = createSpecification(criteria);
        return userRelationApprovalRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link UserRelationApprovalRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserRelationApprovalRules> createSpecification(UserRelationApprovalRulesCriteria criteria) {
        Specification<UserRelationApprovalRules> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserRelationApprovalRules_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), UserRelationApprovalRules_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserRelationApprovalRules_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserRelationApprovalRules_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserRelationApprovalRules_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserRelationApprovalRules_.version));
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(UserRelationApprovalRules_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getLeaveApprovalCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveApprovalCriteriaId(),
                            root ->
                                root.join(UserRelationApprovalRules_.leaveApprovalCriteria, JoinType.LEFT).get(LeaveApprovalCriterias_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
