package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.repository.LeaveDetailAdjustmentLogsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailAdjustmentLogsCriteria;
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
 * Service for executing complex queries for {@link LeaveDetailAdjustmentLogs} entities in the database.
 * The main input is a {@link LeaveDetailAdjustmentLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveDetailAdjustmentLogs} or a {@link Page} of {@link LeaveDetailAdjustmentLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveDetailAdjustmentLogsQueryService extends QueryService<LeaveDetailAdjustmentLogs> {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailAdjustmentLogsQueryService.class);

    private final LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository;

    public LeaveDetailAdjustmentLogsQueryService(LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository) {
        this.leaveDetailAdjustmentLogsRepository = leaveDetailAdjustmentLogsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveDetailAdjustmentLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveDetailAdjustmentLogs> findByCriteria(LeaveDetailAdjustmentLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveDetailAdjustmentLogs> specification = createSpecification(criteria);
        return leaveDetailAdjustmentLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveDetailAdjustmentLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveDetailAdjustmentLogs> findByCriteria(LeaveDetailAdjustmentLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveDetailAdjustmentLogs> specification = createSpecification(criteria);
        return leaveDetailAdjustmentLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveDetailAdjustmentLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveDetailAdjustmentLogs> specification = createSpecification(criteria);
        return leaveDetailAdjustmentLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveDetailAdjustmentLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveDetailAdjustmentLogs> createSpecification(LeaveDetailAdjustmentLogsCriteria criteria) {
        Specification<LeaveDetailAdjustmentLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveDetailAdjustmentLogs_.id));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), LeaveDetailAdjustmentLogs_.action));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), LeaveDetailAdjustmentLogs_.count));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveDetailAdjustmentLogs_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveDetailAdjustmentLogs_.updatedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveDetailAdjustmentLogs_.version));
            }
            if (criteria.getQuotaBeforeAdjustment() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getQuotaBeforeAdjustment(), LeaveDetailAdjustmentLogs_.quotaBeforeAdjustment)
                    );
            }
            if (criteria.getQuotaAfterAdjustment() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getQuotaAfterAdjustment(), LeaveDetailAdjustmentLogs_.quotaAfterAdjustment)
                    );
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), LeaveDetailAdjustmentLogs_.comment));
            }
            if (criteria.getLeaveDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveDetailId(),
                            root -> root.join(LeaveDetailAdjustmentLogs_.leaveDetail, JoinType.LEFT).get(LeaveDetails_.id)
                        )
                    );
            }
            if (criteria.getAdjustedById() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdjustedById(),
                            root -> root.join(LeaveDetailAdjustmentLogs_.adjustedBy, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
