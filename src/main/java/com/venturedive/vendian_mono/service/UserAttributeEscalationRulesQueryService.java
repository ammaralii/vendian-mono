package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.UserAttributeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributeEscalationRulesCriteria;
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
 * Service for executing complex queries for {@link UserAttributeEscalationRules} entities in the database.
 * The main input is a {@link UserAttributeEscalationRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAttributeEscalationRules} or a {@link Page} of {@link UserAttributeEscalationRules} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAttributeEscalationRulesQueryService extends QueryService<UserAttributeEscalationRules> {

    private final Logger log = LoggerFactory.getLogger(UserAttributeEscalationRulesQueryService.class);

    private final UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository;

    public UserAttributeEscalationRulesQueryService(UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository) {
        this.userAttributeEscalationRulesRepository = userAttributeEscalationRulesRepository;
    }

    /**
     * Return a {@link List} of {@link UserAttributeEscalationRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAttributeEscalationRules> findByCriteria(UserAttributeEscalationRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAttributeEscalationRules> specification = createSpecification(criteria);
        return userAttributeEscalationRulesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserAttributeEscalationRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributeEscalationRules> findByCriteria(UserAttributeEscalationRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAttributeEscalationRules> specification = createSpecification(criteria);
        return userAttributeEscalationRulesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAttributeEscalationRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAttributeEscalationRules> specification = createSpecification(criteria);
        return userAttributeEscalationRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAttributeEscalationRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAttributeEscalationRules> createSpecification(UserAttributeEscalationRulesCriteria criteria) {
        Specification<UserAttributeEscalationRules> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAttributeEscalationRules_.id));
            }
            if (criteria.getLeaveEscalationCriteriaId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getLeaveEscalationCriteriaId(),
                            UserAttributeEscalationRules_.leaveEscalationCriteriaId
                        )
                    );
            }
            if (criteria.getNoOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfDays(), UserAttributeEscalationRules_.noOfDays));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), UserAttributeEscalationRules_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserAttributeEscalationRules_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserAttributeEscalationRules_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserAttributeEscalationRules_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserAttributeEscalationRules_.version));
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(UserAttributeEscalationRules_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getApproverStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApproverStatusId(),
                            root -> root.join(UserAttributeEscalationRules_.approverStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getLeaveescalationcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveescalationcriteriaId(),
                            root ->
                                root
                                    .join(UserAttributeEscalationRules_.leaveescalationcriteria, JoinType.LEFT)
                                    .get(LeaveEscalationCriterias_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
