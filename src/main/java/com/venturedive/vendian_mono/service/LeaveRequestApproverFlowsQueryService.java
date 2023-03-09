package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import com.venturedive.vendian_mono.repository.LeaveRequestApproverFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestApproverFlowsCriteria;
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
 * Service for executing complex queries for {@link LeaveRequestApproverFlows} entities in the database.
 * The main input is a {@link LeaveRequestApproverFlowsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveRequestApproverFlows} or a {@link Page} of {@link LeaveRequestApproverFlows} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveRequestApproverFlowsQueryService extends QueryService<LeaveRequestApproverFlows> {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproverFlowsQueryService.class);

    private final LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository;

    public LeaveRequestApproverFlowsQueryService(LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository) {
        this.leaveRequestApproverFlowsRepository = leaveRequestApproverFlowsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveRequestApproverFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveRequestApproverFlows> findByCriteria(LeaveRequestApproverFlowsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveRequestApproverFlows> specification = createSpecification(criteria);
        return leaveRequestApproverFlowsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveRequestApproverFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestApproverFlows> findByCriteria(LeaveRequestApproverFlowsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveRequestApproverFlows> specification = createSpecification(criteria);
        return leaveRequestApproverFlowsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveRequestApproverFlowsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveRequestApproverFlows> specification = createSpecification(criteria);
        return leaveRequestApproverFlowsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveRequestApproverFlowsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveRequestApproverFlows> createSpecification(LeaveRequestApproverFlowsCriteria criteria) {
        Specification<LeaveRequestApproverFlows> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveRequestApproverFlows_.id));
            }
            if (criteria.getApprovals() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovals(), LeaveRequestApproverFlows_.approvals));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveRequestApproverFlows_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveRequestApproverFlows_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveRequestApproverFlows_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveRequestApproverFlows_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveRequestApproverFlows_.version));
            }
            if (criteria.getApproverStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApproverStatusId(),
                            root -> root.join(LeaveRequestApproverFlows_.approverStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getCurrentLeaveRequestStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCurrentLeaveRequestStatusId(),
                            root -> root.join(LeaveRequestApproverFlows_.currentLeaveRequestStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getNextLeaveRequestStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNextLeaveRequestStatusId(),
                            root -> root.join(LeaveRequestApproverFlows_.nextLeaveRequestStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
