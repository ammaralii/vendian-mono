package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.repository.LeaveRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsCriteria;
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
 * Service for executing complex queries for {@link LeaveRequests} entities in the database.
 * The main input is a {@link LeaveRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveRequests} or a {@link Page} of {@link LeaveRequests} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveRequestsQueryService extends QueryService<LeaveRequests> {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsQueryService.class);

    private final LeaveRequestsRepository leaveRequestsRepository;

    public LeaveRequestsQueryService(LeaveRequestsRepository leaveRequestsRepository) {
        this.leaveRequestsRepository = leaveRequestsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveRequests> findByCriteria(LeaveRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveRequests> specification = createSpecification(criteria);
        return leaveRequestsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequests> findByCriteria(LeaveRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveRequests> specification = createSpecification(criteria);
        return leaveRequestsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveRequests> specification = createSpecification(criteria);
        return leaveRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveRequests> createSpecification(LeaveRequestsCriteria criteria) {
        Specification<LeaveRequests> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveRequests_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveRequests_.createdAt));
            }
            if (criteria.getRequestStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestStartDate(), LeaveRequests_.requestStartDate));
            }
            if (criteria.getRequestEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestEndDate(), LeaveRequests_.requestEndDate));
            }
            if (criteria.getIsHalfDay() != null) {
                specification = specification.and(buildSpecification(criteria.getIsHalfDay(), LeaveRequests_.isHalfDay));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), LeaveRequests_.statusDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), LeaveRequests_.comments));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveRequests_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), LeaveRequests_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveRequests_.version));
            }
            if (criteria.getLeaveDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveDetailId(),
                            root -> root.join(LeaveRequests_.leaveDetail, JoinType.LEFT).get(LeaveDetails_.id)
                        )
                    );
            }
            if (criteria.getLeaveStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveStatusId(),
                            root -> root.join(LeaveRequests_.leaveStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getParentLeaveRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentLeaveRequestId(),
                            root -> root.join(LeaveRequests_.parentLeaveRequest, JoinType.LEFT).get(LeaveRequests_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestapproversLeaverequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestapproversLeaverequestId(),
                            root ->
                                root.join(LeaveRequests_.leaverequestapproversLeaverequests, JoinType.LEFT).get(LeaveRequestApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestsParentleaverequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsParentleaverequestId(),
                            root -> root.join(LeaveRequests_.leaverequestsParentleaverequests, JoinType.LEFT).get(LeaveRequests_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
