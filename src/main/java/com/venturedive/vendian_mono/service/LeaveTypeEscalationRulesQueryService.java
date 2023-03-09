package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.repository.LeaveTypeEscalationRulesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeEscalationRulesCriteria;
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
 * Service for executing complex queries for {@link LeaveTypeEscalationRules} entities in the database.
 * The main input is a {@link LeaveTypeEscalationRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypeEscalationRules} or a {@link Page} of {@link LeaveTypeEscalationRules} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeEscalationRulesQueryService extends QueryService<LeaveTypeEscalationRules> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeEscalationRulesQueryService.class);

    private final LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository;

    public LeaveTypeEscalationRulesQueryService(LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository) {
        this.leaveTypeEscalationRulesRepository = leaveTypeEscalationRulesRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypeEscalationRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypeEscalationRules> findByCriteria(LeaveTypeEscalationRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypeEscalationRules> specification = createSpecification(criteria);
        return leaveTypeEscalationRulesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypeEscalationRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeEscalationRules> findByCriteria(LeaveTypeEscalationRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypeEscalationRules> specification = createSpecification(criteria);
        return leaveTypeEscalationRulesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeEscalationRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypeEscalationRules> specification = createSpecification(criteria);
        return leaveTypeEscalationRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypeEscalationRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypeEscalationRules> createSpecification(LeaveTypeEscalationRulesCriteria criteria) {
        Specification<LeaveTypeEscalationRules> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypeEscalationRules_.id));
            }
            if (criteria.getNoOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfDays(), LeaveTypeEscalationRules_.noOfDays));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveTypeEscalationRules_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveTypeEscalationRules_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveTypeEscalationRules_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveTypeEscalationRules_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveTypeEscalationRules_.version));
            }
            if (criteria.getLeaveEscalationCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveEscalationCriteriaId(),
                            root ->
                                root
                                    .join(LeaveTypeEscalationRules_.leaveEscalationCriteria, JoinType.LEFT)
                                    .get(LeaveEscalationCriterias_.id)
                        )
                    );
            }
            if (criteria.getLeaveRequestStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveRequestStatusId(),
                            root -> root.join(LeaveTypeEscalationRules_.leaveRequestStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getLeaveTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveTypeId(),
                            root -> root.join(LeaveTypeEscalationRules_.leaveType, JoinType.LEFT).get(LeaveTypes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
