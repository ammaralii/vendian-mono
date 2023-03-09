package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.repository.LeaveRequestApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproversCriteria;
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
 * Service for executing complex queries for {@link LeaveRequestApprovers} entities in the database.
 * The main input is a {@link LeaveRequestApproversCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveRequestApprovers} or a {@link Page} of {@link LeaveRequestApprovers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveRequestApproversQueryService extends QueryService<LeaveRequestApprovers> {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproversQueryService.class);

    private final LeaveRequestApproversRepository leaveRequestApproversRepository;

    public LeaveRequestApproversQueryService(LeaveRequestApproversRepository leaveRequestApproversRepository) {
        this.leaveRequestApproversRepository = leaveRequestApproversRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveRequestApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveRequestApprovers> findByCriteria(LeaveRequestApproversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveRequestApprovers> specification = createSpecification(criteria);
        return leaveRequestApproversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveRequestApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestApprovers> findByCriteria(LeaveRequestApproversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveRequestApprovers> specification = createSpecification(criteria);
        return leaveRequestApproversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveRequestApproversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveRequestApprovers> specification = createSpecification(criteria);
        return leaveRequestApproversRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveRequestApproversCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveRequestApprovers> createSpecification(LeaveRequestApproversCriteria criteria) {
        Specification<LeaveRequestApprovers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveRequestApprovers_.id));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), LeaveRequestApprovers_.reference));
            }
            if (criteria.getAs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAs(), LeaveRequestApprovers_.as));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), LeaveRequestApprovers_.comments));
            }
            if (criteria.getApproverGroup() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getApproverGroup(), LeaveRequestApprovers_.approverGroup));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), LeaveRequestApprovers_.priority));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), LeaveRequestApprovers_.statusDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveRequestApprovers_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveRequestApprovers_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), LeaveRequestApprovers_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveRequestApprovers_.version));
            }
            if (criteria.getLeaveRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveRequestId(),
                            root -> root.join(LeaveRequestApprovers_.leaveRequest, JoinType.LEFT).get(LeaveRequests_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(LeaveRequestApprovers_.user, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusId(),
                            root -> root.join(LeaveRequestApprovers_.status, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
