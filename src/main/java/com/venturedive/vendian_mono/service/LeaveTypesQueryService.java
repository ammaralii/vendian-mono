package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesCriteria;
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
 * Service for executing complex queries for {@link LeaveTypes} entities in the database.
 * The main input is a {@link LeaveTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypes} or a {@link Page} of {@link LeaveTypes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypesQueryService extends QueryService<LeaveTypes> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesQueryService.class);

    private final LeaveTypesRepository leaveTypesRepository;

    public LeaveTypesQueryService(LeaveTypesRepository leaveTypesRepository) {
        this.leaveTypesRepository = leaveTypesRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypes> findByCriteria(LeaveTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypes> specification = createSpecification(criteria);
        return leaveTypesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypes> findByCriteria(LeaveTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypes> specification = createSpecification(criteria);
        return leaveTypesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypes> specification = createSpecification(criteria);
        return leaveTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypes> createSpecification(LeaveTypesCriteria criteria) {
        Specification<LeaveTypes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveTypes_.name));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), LeaveTypes_.category));
            }
            if (criteria.getCycleType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCycleType(), LeaveTypes_.cycleType));
            }
            if (criteria.getCycleCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCycleCount(), LeaveTypes_.cycleCount));
            }
            if (criteria.getMaxQuota() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxQuota(), LeaveTypes_.maxQuota));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveTypes_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveTypes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveTypes_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveTypes_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveTypes_.version));
            }
            if (criteria.getLeavedetailsLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavedetailsLeavetypeId(),
                            root -> root.join(LeaveTypes_.leavedetailsLeavetypes, JoinType.LEFT).get(LeaveDetails_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeapprovalrulesLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeapprovalrulesLeavetypeId(),
                            root -> root.join(LeaveTypes_.leavetypeapprovalrulesLeavetypes, JoinType.LEFT).get(LeaveTypeApprovalRules_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeconfigurationsLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeconfigurationsLeavetypeId(),
                            root -> root.join(LeaveTypes_.leavetypeconfigurationsLeavetypes, JoinType.LEFT).get(LeaveTypeConfigurations_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeescalationrulesLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeescalationrulesLeavetypeId(),
                            root ->
                                root.join(LeaveTypes_.leavetypeescalationrulesLeavetypes, JoinType.LEFT).get(LeaveTypeEscalationRules_.id)
                        )
                    );
            }
            if (criteria.getLeavetyperestrictionsLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetyperestrictionsLeavetypeId(),
                            root -> root.join(LeaveTypes_.leavetyperestrictionsLeavetypes, JoinType.LEFT).get(LeaveTypeRestrictions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
