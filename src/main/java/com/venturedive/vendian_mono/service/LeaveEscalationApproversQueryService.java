package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.repository.LeaveEscalationApproversRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationApproversCriteria;
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
 * Service for executing complex queries for {@link LeaveEscalationApprovers} entities in the database.
 * The main input is a {@link LeaveEscalationApproversCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveEscalationApprovers} or a {@link Page} of {@link LeaveEscalationApprovers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveEscalationApproversQueryService extends QueryService<LeaveEscalationApprovers> {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationApproversQueryService.class);

    private final LeaveEscalationApproversRepository leaveEscalationApproversRepository;

    public LeaveEscalationApproversQueryService(LeaveEscalationApproversRepository leaveEscalationApproversRepository) {
        this.leaveEscalationApproversRepository = leaveEscalationApproversRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveEscalationApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveEscalationApprovers> findByCriteria(LeaveEscalationApproversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveEscalationApprovers> specification = createSpecification(criteria);
        return leaveEscalationApproversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveEscalationApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveEscalationApprovers> findByCriteria(LeaveEscalationApproversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveEscalationApprovers> specification = createSpecification(criteria);
        return leaveEscalationApproversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveEscalationApproversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveEscalationApprovers> specification = createSpecification(criteria);
        return leaveEscalationApproversRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveEscalationApproversCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveEscalationApprovers> createSpecification(LeaveEscalationApproversCriteria criteria) {
        Specification<LeaveEscalationApprovers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveEscalationApprovers_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), LeaveEscalationApprovers_.source));
            }
            if (criteria.getMinApprovals() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMinApprovals(), LeaveEscalationApprovers_.minApprovals));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), LeaveEscalationApprovers_.priority));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveEscalationApprovers_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveEscalationApprovers_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveEscalationApprovers_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveEscalationApprovers_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveEscalationApprovers_.version));
            }
            if (criteria.getLeaveEscalationCriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveEscalationCriteriaId(),
                            root ->
                                root
                                    .join(LeaveEscalationApprovers_.leaveEscalationCriteria, JoinType.LEFT)
                                    .get(LeaveEscalationCriterias_.id)
                        )
                    );
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(LeaveEscalationApprovers_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
