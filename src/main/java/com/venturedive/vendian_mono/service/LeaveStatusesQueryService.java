package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusesCriteria;
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
 * Service for executing complex queries for {@link LeaveStatuses} entities in the database.
 * The main input is a {@link LeaveStatusesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveStatuses} or a {@link Page} of {@link LeaveStatuses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveStatusesQueryService extends QueryService<LeaveStatuses> {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusesQueryService.class);

    private final LeaveStatusesRepository leaveStatusesRepository;

    public LeaveStatusesQueryService(LeaveStatusesRepository leaveStatusesRepository) {
        this.leaveStatusesRepository = leaveStatusesRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveStatuses> findByCriteria(LeaveStatusesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveStatuses> specification = createSpecification(criteria);
        return leaveStatusesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveStatuses> findByCriteria(LeaveStatusesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveStatuses> specification = createSpecification(criteria);
        return leaveStatusesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveStatusesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveStatuses> specification = createSpecification(criteria);
        return leaveStatusesRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveStatusesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveStatuses> createSpecification(LeaveStatusesCriteria criteria) {
        Specification<LeaveStatuses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveStatuses_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveStatuses_.name));
            }
            if (criteria.getLeaveGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeaveGroup(), LeaveStatuses_.leaveGroup));
            }
            if (criteria.getSystemKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSystemKey(), LeaveStatuses_.systemKey));
            }
            if (criteria.getIsDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDefault(), LeaveStatuses_.isDefault));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveStatuses_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveStatuses_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveStatuses_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveStatuses_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveStatuses_.version));
            }
            if (criteria.getLeaverequestapproverflowsApproverstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproverflowsApproverstatusId(),
                            root ->
                                root
                                    .join(LeaveStatuses_.leaverequestapproverflowsApproverstatuses, JoinType.LEFT)
                                    .get(LeaveRequestApproverFlows_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestapproverflowsCurrentleaverequeststatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproverflowsCurrentleaverequeststatusId(),
                            root ->
                                root
                                    .join(LeaveStatuses_.leaverequestapproverflowsCurrentleaverequeststatuses, JoinType.LEFT)
                                    .get(LeaveRequestApproverFlows_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestapproverflowsNextleaverequeststatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproverflowsNextleaverequeststatusId(),
                            root ->
                                root
                                    .join(LeaveStatuses_.leaverequestapproverflowsNextleaverequeststatuses, JoinType.LEFT)
                                    .get(LeaveRequestApproverFlows_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestapproversStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproversStatusId(),
                            root -> root.join(LeaveStatuses_.leaverequestapproversStatuses, JoinType.LEFT).get(LeaveRequestApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestsLeavestatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsLeavestatusId(),
                            root -> root.join(LeaveStatuses_.leaverequestsLeavestatuses, JoinType.LEFT).get(LeaveRequests_.id)
                        )
                    );
            }
            if (criteria.getLeavestatusworkflowsCurrentstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavestatusworkflowsCurrentstatusId(),
                            root ->
                                root.join(LeaveStatuses_.leavestatusworkflowsCurrentstatuses, JoinType.LEFT).get(LeaveStatusWorkFlows_.id)
                        )
                    );
            }
            if (criteria.getLeavestatusworkflowsNextstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavestatusworkflowsNextstatusId(),
                            root -> root.join(LeaveStatuses_.leavestatusworkflowsNextstatuses, JoinType.LEFT).get(LeaveStatusWorkFlows_.id)
                        )
                    );
            }
            if (criteria.getLeavestatusworkflowsSkiptostatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavestatusworkflowsSkiptostatusId(),
                            root ->
                                root.join(LeaveStatuses_.leavestatusworkflowsSkiptostatuses, JoinType.LEFT).get(LeaveStatusWorkFlows_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeescalationrulesLeaverequeststatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeescalationrulesLeaverequeststatusId(),
                            root ->
                                root
                                    .join(LeaveStatuses_.leavetypeescalationrulesLeaverequeststatuses, JoinType.LEFT)
                                    .get(LeaveTypeEscalationRules_.id)
                        )
                    );
            }
            if (criteria.getUserattributeescalationrulesApproverstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributeescalationrulesApproverstatusId(),
                            root ->
                                root
                                    .join(LeaveStatuses_.userattributeescalationrulesApproverstatuses, JoinType.LEFT)
                                    .get(UserAttributeEscalationRules_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
