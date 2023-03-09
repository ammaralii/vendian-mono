package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.repository.LeaveApprovalCriteriasRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveApprovalCriteriasCriteria;
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
 * Service for executing complex queries for {@link LeaveApprovalCriterias} entities in the database.
 * The main input is a {@link LeaveApprovalCriteriasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveApprovalCriterias} or a {@link Page} of {@link LeaveApprovalCriterias} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveApprovalCriteriasQueryService extends QueryService<LeaveApprovalCriterias> {

    private final Logger log = LoggerFactory.getLogger(LeaveApprovalCriteriasQueryService.class);

    private final LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository;

    public LeaveApprovalCriteriasQueryService(LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository) {
        this.leaveApprovalCriteriasRepository = leaveApprovalCriteriasRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveApprovalCriterias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveApprovalCriterias> findByCriteria(LeaveApprovalCriteriasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveApprovalCriterias> specification = createSpecification(criteria);
        return leaveApprovalCriteriasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveApprovalCriterias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApprovalCriterias> findByCriteria(LeaveApprovalCriteriasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveApprovalCriterias> specification = createSpecification(criteria);
        return leaveApprovalCriteriasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveApprovalCriteriasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveApprovalCriterias> specification = createSpecification(criteria);
        return leaveApprovalCriteriasRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveApprovalCriteriasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveApprovalCriterias> createSpecification(LeaveApprovalCriteriasCriteria criteria) {
        Specification<LeaveApprovalCriterias> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveApprovalCriterias_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveApprovalCriterias_.name));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), LeaveApprovalCriterias_.priority));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveApprovalCriterias_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveApprovalCriterias_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveApprovalCriterias_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveApprovalCriterias_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveApprovalCriterias_.version));
            }
            if (criteria.getLeaveapproversLeaveapprovalcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveapproversLeaveapprovalcriteriaId(),
                            root ->
                                root
                                    .join(LeaveApprovalCriterias_.leaveapproversLeaveapprovalcriteria, JoinType.LEFT)
                                    .get(LeaveApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeapprovalrulesLeaveapprovalcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeapprovalrulesLeaveapprovalcriteriaId(),
                            root ->
                                root
                                    .join(LeaveApprovalCriterias_.leavetypeapprovalrulesLeaveapprovalcriteria, JoinType.LEFT)
                                    .get(LeaveTypeApprovalRules_.id)
                        )
                    );
            }
            if (criteria.getUserattributeapprovalrulesLeaveapprovalcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributeapprovalrulesLeaveapprovalcriteriaId(),
                            root ->
                                root
                                    .join(LeaveApprovalCriterias_.userattributeapprovalrulesLeaveapprovalcriteria, JoinType.LEFT)
                                    .get(UserAttributeApprovalRules_.id)
                        )
                    );
            }
            if (criteria.getUserrelationapprovalrulesLeaveapprovalcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserrelationapprovalrulesLeaveapprovalcriteriaId(),
                            root ->
                                root
                                    .join(LeaveApprovalCriterias_.userrelationapprovalrulesLeaveapprovalcriteria, JoinType.LEFT)
                                    .get(UserRelationApprovalRules_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
