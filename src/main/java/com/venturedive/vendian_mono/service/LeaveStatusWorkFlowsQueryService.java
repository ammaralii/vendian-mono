package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import com.venturedive.vendian_mono.repository.LeaveStatusWorkFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveStatusWorkFlowsCriteria;
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
 * Service for executing complex queries for {@link LeaveStatusWorkFlows} entities in the database.
 * The main input is a {@link LeaveStatusWorkFlowsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveStatusWorkFlows} or a {@link Page} of {@link LeaveStatusWorkFlows} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveStatusWorkFlowsQueryService extends QueryService<LeaveStatusWorkFlows> {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusWorkFlowsQueryService.class);

    private final LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository;

    public LeaveStatusWorkFlowsQueryService(LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository) {
        this.leaveStatusWorkFlowsRepository = leaveStatusWorkFlowsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveStatusWorkFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveStatusWorkFlows> findByCriteria(LeaveStatusWorkFlowsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveStatusWorkFlows> specification = createSpecification(criteria);
        return leaveStatusWorkFlowsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveStatusWorkFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveStatusWorkFlows> findByCriteria(LeaveStatusWorkFlowsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveStatusWorkFlows> specification = createSpecification(criteria);
        return leaveStatusWorkFlowsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveStatusWorkFlowsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveStatusWorkFlows> specification = createSpecification(criteria);
        return leaveStatusWorkFlowsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveStatusWorkFlowsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveStatusWorkFlows> createSpecification(LeaveStatusWorkFlowsCriteria criteria) {
        Specification<LeaveStatusWorkFlows> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveStatusWorkFlows_.id));
            }
            if (criteria.getIfApprovals() != null) {
                specification = specification.and(buildSpecification(criteria.getIfApprovals(), LeaveStatusWorkFlows_.ifApprovals));
            }
            if (criteria.getApprovalRequired() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getApprovalRequired(), LeaveStatusWorkFlows_.approvalRequired));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveStatusWorkFlows_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveStatusWorkFlows_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), LeaveStatusWorkFlows_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveStatusWorkFlows_.version));
            }
            if (criteria.getCurrentStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCurrentStatusId(),
                            root -> root.join(LeaveStatusWorkFlows_.currentStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getNextStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNextStatusId(),
                            root -> root.join(LeaveStatusWorkFlows_.nextStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
            if (criteria.getSkipToStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSkipToStatusId(),
                            root -> root.join(LeaveStatusWorkFlows_.skipToStatus, JoinType.LEFT).get(LeaveStatuses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
