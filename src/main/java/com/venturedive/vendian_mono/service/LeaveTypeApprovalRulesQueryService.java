package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.repository.LeaveTypeApprovalRulesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeApprovalRulesCriteria;
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
 * Service for executing complex queries for {@link LeaveTypeApprovalRules} entities in the database.
 * The main input is a {@link LeaveTypeApprovalRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypeApprovalRules} or a {@link Page} of {@link LeaveTypeApprovalRules} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeApprovalRulesQueryService extends QueryService<LeaveTypeApprovalRules> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeApprovalRulesQueryService.class);

    private final LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository;

    public LeaveTypeApprovalRulesQueryService(LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository) {
        this.leaveTypeApprovalRulesRepository = leaveTypeApprovalRulesRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypeApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypeApprovalRules> findByCriteria(LeaveTypeApprovalRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypeApprovalRules> specification = createSpecification(criteria);
        return leaveTypeApprovalRulesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypeApprovalRules} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeApprovalRules> findByCriteria(LeaveTypeApprovalRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypeApprovalRules> specification = createSpecification(criteria);
        return leaveTypeApprovalRulesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeApprovalRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypeApprovalRules> specification = createSpecification(criteria);
        return leaveTypeApprovalRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypeApprovalRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypeApprovalRules> createSpecification(LeaveTypeApprovalRulesCriteria criteria) {
        Specification<LeaveTypeApprovalRules> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypeApprovalRules_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveTypeApprovalRules_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveTypeApprovalRules_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveTypeApprovalRules_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), LeaveTypeApprovalRules_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveTypeApprovalRules_.version));
            }
            if (criteria.getLeaveApprovalCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveApprovalCriteriaId(),
                            root -> root.join(LeaveTypeApprovalRules_.leaveApprovalCriteria, JoinType.LEFT).get(LeaveApprovalCriterias_.id)
                        )
                    );
            }
            if (criteria.getLeaveTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveTypeId(),
                            root -> root.join(LeaveTypeApprovalRules_.leaveType, JoinType.LEFT).get(LeaveTypes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
