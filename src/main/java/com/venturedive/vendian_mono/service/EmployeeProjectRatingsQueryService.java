package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatingsCriteria;
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
 * Service for executing complex queries for {@link EmployeeProjectRatings} entities in the database.
 * The main input is a {@link EmployeeProjectRatingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeProjectRatings} or a {@link Page} of {@link EmployeeProjectRatings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeProjectRatingsQueryService extends QueryService<EmployeeProjectRatings> {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatingsQueryService.class);

    private final EmployeeProjectRatingsRepository employeeProjectRatingsRepository;

    public EmployeeProjectRatingsQueryService(EmployeeProjectRatingsRepository employeeProjectRatingsRepository) {
        this.employeeProjectRatingsRepository = employeeProjectRatingsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeProjectRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProjectRatings> findByCriteria(EmployeeProjectRatingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeProjectRatings> specification = createSpecification(criteria);
        return employeeProjectRatingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeProjectRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRatings> findByCriteria(EmployeeProjectRatingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeProjectRatings> specification = createSpecification(criteria);
        return employeeProjectRatingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeProjectRatingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeProjectRatings> specification = createSpecification(criteria);
        return employeeProjectRatingsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeProjectRatingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeProjectRatings> createSpecification(EmployeeProjectRatingsCriteria criteria) {
        Specification<EmployeeProjectRatings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeProjectRatings_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeProjectRatings_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeProjectRatings_.updatedat));
            }
            if (criteria.getProjectarchitectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectarchitectId(),
                            root -> root.join(EmployeeProjectRatings_.projectarchitect, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectmanagerId(),
                            root -> root.join(EmployeeProjectRatings_.projectmanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeProjectRatings_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectcycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcycleId(),
                            root -> root.join(EmployeeProjectRatings_.projectcycle, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
