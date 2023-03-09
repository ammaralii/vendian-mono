package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.repository.LeaveDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveDetailsCriteria;
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
 * Service for executing complex queries for {@link LeaveDetails} entities in the database.
 * The main input is a {@link LeaveDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveDetails} or a {@link Page} of {@link LeaveDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveDetailsQueryService extends QueryService<LeaveDetails> {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailsQueryService.class);

    private final LeaveDetailsRepository leaveDetailsRepository;

    public LeaveDetailsQueryService(LeaveDetailsRepository leaveDetailsRepository) {
        this.leaveDetailsRepository = leaveDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveDetails> findByCriteria(LeaveDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveDetails> specification = createSpecification(criteria);
        return leaveDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveDetails> findByCriteria(LeaveDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveDetails> specification = createSpecification(criteria);
        return leaveDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveDetails> specification = createSpecification(criteria);
        return leaveDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveDetails> createSpecification(LeaveDetailsCriteria criteria) {
        Specification<LeaveDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveDetails_.id));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), LeaveDetails_.total));
            }
            if (criteria.getUsed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUsed(), LeaveDetails_.used));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveDetails_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveDetails_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveDetails_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveDetails_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveDetails_.version));
            }
            if (criteria.getLeaveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLeaveId(), root -> root.join(LeaveDetails_.leave, JoinType.LEFT).get(Leaves_.id))
                    );
            }
            if (criteria.getLeaveTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveTypeId(),
                            root -> root.join(LeaveDetails_.leaveType, JoinType.LEFT).get(LeaveTypes_.id)
                        )
                    );
            }
            if (criteria.getLeavedetailadjustmentlogsLeavedetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavedetailadjustmentlogsLeavedetailId(),
                            root ->
                                root
                                    .join(LeaveDetails_.leavedetailadjustmentlogsLeavedetails, JoinType.LEFT)
                                    .get(LeaveDetailAdjustmentLogs_.id)
                        )
                    );
            }
            if (criteria.getLeaverequestsLeavedetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsLeavedetailId(),
                            root -> root.join(LeaveDetails_.leaverequestsLeavedetails, JoinType.LEFT).get(LeaveRequests_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
