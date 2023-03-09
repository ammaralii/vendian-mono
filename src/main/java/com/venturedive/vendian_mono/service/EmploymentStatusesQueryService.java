package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import com.venturedive.vendian_mono.repository.EmploymentStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentStatusesCriteria;
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
 * Service for executing complex queries for {@link EmploymentStatuses} entities in the database.
 * The main input is a {@link EmploymentStatusesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmploymentStatuses} or a {@link Page} of {@link EmploymentStatuses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmploymentStatusesQueryService extends QueryService<EmploymentStatuses> {

    private final Logger log = LoggerFactory.getLogger(EmploymentStatusesQueryService.class);

    private final EmploymentStatusesRepository employmentStatusesRepository;

    public EmploymentStatusesQueryService(EmploymentStatusesRepository employmentStatusesRepository) {
        this.employmentStatusesRepository = employmentStatusesRepository;
    }

    /**
     * Return a {@link List} of {@link EmploymentStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmploymentStatuses> findByCriteria(EmploymentStatusesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmploymentStatuses> specification = createSpecification(criteria);
        return employmentStatusesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmploymentStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentStatuses> findByCriteria(EmploymentStatusesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmploymentStatuses> specification = createSpecification(criteria);
        return employmentStatusesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmploymentStatusesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmploymentStatuses> specification = createSpecification(criteria);
        return employmentStatusesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmploymentStatusesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmploymentStatuses> createSpecification(EmploymentStatusesCriteria criteria) {
        Specification<EmploymentStatuses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmploymentStatuses_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmploymentStatuses_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmploymentStatuses_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmploymentStatuses_.updatedat));
            }
            if (criteria.getEmployeesEmploymentstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesEmploymentstatusId(),
                            root -> root.join(EmploymentStatuses_.employeesEmploymentstatuses, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
