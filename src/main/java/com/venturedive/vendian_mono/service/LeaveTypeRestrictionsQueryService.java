package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import com.venturedive.vendian_mono.repository.LeaveTypeRestrictionsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeRestrictionsCriteria;
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
 * Service for executing complex queries for {@link LeaveTypeRestrictions} entities in the database.
 * The main input is a {@link LeaveTypeRestrictionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypeRestrictions} or a {@link Page} of {@link LeaveTypeRestrictions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeRestrictionsQueryService extends QueryService<LeaveTypeRestrictions> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeRestrictionsQueryService.class);

    private final LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository;

    public LeaveTypeRestrictionsQueryService(LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository) {
        this.leaveTypeRestrictionsRepository = leaveTypeRestrictionsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypeRestrictions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypeRestrictions> findByCriteria(LeaveTypeRestrictionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypeRestrictions> specification = createSpecification(criteria);
        return leaveTypeRestrictionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypeRestrictions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeRestrictions> findByCriteria(LeaveTypeRestrictionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypeRestrictions> specification = createSpecification(criteria);
        return leaveTypeRestrictionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeRestrictionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypeRestrictions> specification = createSpecification(criteria);
        return leaveTypeRestrictionsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypeRestrictionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypeRestrictions> createSpecification(LeaveTypeRestrictionsCriteria criteria) {
        Specification<LeaveTypeRestrictions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypeRestrictions_.id));
            }
            if (criteria.getRestrictionJson() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRestrictionJson(), LeaveTypeRestrictions_.restrictionJson));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveTypeRestrictions_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveTypeRestrictions_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveTypeRestrictions_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveTypeRestrictions_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveTypeRestrictions_.version));
            }
            if (criteria.getLeaveTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveTypeId(),
                            root -> root.join(LeaveTypeRestrictions_.leaveType, JoinType.LEFT).get(LeaveTypes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
