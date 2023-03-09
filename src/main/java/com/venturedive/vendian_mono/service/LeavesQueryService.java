package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Leaves;
import com.venturedive.vendian_mono.repository.LeavesRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesCriteria;
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
 * Service for executing complex queries for {@link Leaves} entities in the database.
 * The main input is a {@link LeavesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Leaves} or a {@link Page} of {@link Leaves} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeavesQueryService extends QueryService<Leaves> {

    private final Logger log = LoggerFactory.getLogger(LeavesQueryService.class);

    private final LeavesRepository leavesRepository;

    public LeavesQueryService(LeavesRepository leavesRepository) {
        this.leavesRepository = leavesRepository;
    }

    /**
     * Return a {@link List} of {@link Leaves} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Leaves> findByCriteria(LeavesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Leaves> specification = createSpecification(criteria);
        return leavesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Leaves} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Leaves> findByCriteria(LeavesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Leaves> specification = createSpecification(criteria);
        return leavesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeavesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Leaves> specification = createSpecification(criteria);
        return leavesRepository.count(specification);
    }

    /**
     * Function to convert {@link LeavesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Leaves> createSpecification(LeavesCriteria criteria) {
        Specification<Leaves> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Leaves_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), Leaves_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Leaves_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Leaves_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Leaves_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Leaves_.version));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(Leaves_.user, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getLeavedetailsLeaveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavedetailsLeaveId(),
                            root -> root.join(Leaves_.leavedetailsLeaves, JoinType.LEFT).get(LeaveDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
