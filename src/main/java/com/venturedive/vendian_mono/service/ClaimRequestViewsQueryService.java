package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import com.venturedive.vendian_mono.repository.ClaimRequestViewsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestViewsCriteria;
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
 * Service for executing complex queries for {@link ClaimRequestViews} entities in the database.
 * The main input is a {@link ClaimRequestViewsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimRequestViews} or a {@link Page} of {@link ClaimRequestViews} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimRequestViewsQueryService extends QueryService<ClaimRequestViews> {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestViewsQueryService.class);

    private final ClaimRequestViewsRepository claimRequestViewsRepository;

    public ClaimRequestViewsQueryService(ClaimRequestViewsRepository claimRequestViewsRepository) {
        this.claimRequestViewsRepository = claimRequestViewsRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimRequestViews} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimRequestViews> findByCriteria(ClaimRequestViewsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimRequestViews> specification = createSpecification(criteria);
        return claimRequestViewsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimRequestViews} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimRequestViews> findByCriteria(ClaimRequestViewsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimRequestViews> specification = createSpecification(criteria);
        return claimRequestViewsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimRequestViewsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimRequestViews> specification = createSpecification(criteria);
        return claimRequestViewsRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimRequestViewsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimRequestViews> createSpecification(ClaimRequestViewsCriteria criteria) {
        Specification<ClaimRequestViews> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimRequestViews_.id));
            }
            if (criteria.getCostcenter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCostcenter(), ClaimRequestViews_.costcenter));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ClaimRequestViews_.comments));
            }
            if (criteria.getAmountreleased() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountreleased(), ClaimRequestViews_.amountreleased));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), ClaimRequestViews_.designation));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), ClaimRequestViews_.department));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), ClaimRequestViews_.location));
            }
            if (criteria.getManager() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManager(), ClaimRequestViews_.manager));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimRequestViews_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimRequestViews_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ClaimRequestViews_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
