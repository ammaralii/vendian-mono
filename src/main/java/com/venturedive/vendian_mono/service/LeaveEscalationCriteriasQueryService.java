package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.repository.LeaveEscalationCriteriasRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveEscalationCriteriasCriteria;
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
 * Service for executing complex queries for {@link LeaveEscalationCriterias} entities in the database.
 * The main input is a {@link LeaveEscalationCriteriasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveEscalationCriterias} or a {@link Page} of {@link LeaveEscalationCriterias} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveEscalationCriteriasQueryService extends QueryService<LeaveEscalationCriterias> {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationCriteriasQueryService.class);

    private final LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository;

    public LeaveEscalationCriteriasQueryService(LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository) {
        this.leaveEscalationCriteriasRepository = leaveEscalationCriteriasRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveEscalationCriterias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveEscalationCriterias> findByCriteria(LeaveEscalationCriteriasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveEscalationCriterias> specification = createSpecification(criteria);
        return leaveEscalationCriteriasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveEscalationCriterias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveEscalationCriterias> findByCriteria(LeaveEscalationCriteriasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveEscalationCriterias> specification = createSpecification(criteria);
        return leaveEscalationCriteriasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveEscalationCriteriasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveEscalationCriterias> specification = createSpecification(criteria);
        return leaveEscalationCriteriasRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveEscalationCriteriasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveEscalationCriterias> createSpecification(LeaveEscalationCriteriasCriteria criteria) {
        Specification<LeaveEscalationCriterias> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveEscalationCriterias_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveEscalationCriterias_.name));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), LeaveEscalationCriterias_.priority));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), LeaveEscalationCriterias_.total));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveEscalationCriterias_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveEscalationCriterias_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveEscalationCriterias_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveEscalationCriterias_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveEscalationCriterias_.version));
            }
            if (criteria.getLeaveescalationapproversLeaveescalationcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveescalationapproversLeaveescalationcriteriaId(),
                            root ->
                                root
                                    .join(LeaveEscalationCriterias_.leaveescalationapproversLeaveescalationcriteria, JoinType.LEFT)
                                    .get(LeaveEscalationApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeavetypeescalationrulesLeaveescalationcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeescalationrulesLeaveescalationcriteriaId(),
                            root ->
                                root
                                    .join(LeaveEscalationCriterias_.leavetypeescalationrulesLeaveescalationcriteria, JoinType.LEFT)
                                    .get(LeaveTypeEscalationRules_.id)
                        )
                    );
            }
            if (criteria.getUserattributeescalationrulesLeaveescalationcriteriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributeescalationrulesLeaveescalationcriteriaId(),
                            root ->
                                root
                                    .join(LeaveEscalationCriterias_.userattributeescalationrulesLeaveescalationcriteria, JoinType.LEFT)
                                    .get(UserAttributeEscalationRules_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
