package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.repository.LeaveApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveApproversCriteria;
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
 * Service for executing complex queries for {@link LeaveApprovers} entities in the database.
 * The main input is a {@link LeaveApproversCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveApprovers} or a {@link Page} of {@link LeaveApprovers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveApproversQueryService extends QueryService<LeaveApprovers> {

    private final Logger log = LoggerFactory.getLogger(LeaveApproversQueryService.class);

    private final LeaveApproversRepository leaveApproversRepository;

    public LeaveApproversQueryService(LeaveApproversRepository leaveApproversRepository) {
        this.leaveApproversRepository = leaveApproversRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveApprovers> findByCriteria(LeaveApproversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveApprovers> specification = createSpecification(criteria);
        return leaveApproversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApprovers> findByCriteria(LeaveApproversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveApprovers> specification = createSpecification(criteria);
        return leaveApproversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveApproversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveApprovers> specification = createSpecification(criteria);
        return leaveApproversRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveApproversCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveApprovers> createSpecification(LeaveApproversCriteria criteria) {
        Specification<LeaveApprovers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveApprovers_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), LeaveApprovers_.source));
            }
            if (criteria.getMinApprovals() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinApprovals(), LeaveApprovers_.minApprovals));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), LeaveApprovers_.priority));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveApprovers_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveApprovers_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveApprovers_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveApprovers_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveApprovers_.version));
            }
            if (criteria.getLeaveApprovalCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveApprovalCriteriaId(),
                            root -> root.join(LeaveApprovers_.leaveApprovalCriteria, JoinType.LEFT).get(LeaveApprovalCriterias_.id)
                        )
                    );
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(LeaveApprovers_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
