package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeWorks;
import com.venturedive.vendian_mono.repository.EmployeeWorksRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeWorksCriteria;
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
 * Service for executing complex queries for {@link EmployeeWorks} entities in the database.
 * The main input is a {@link EmployeeWorksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeWorks} or a {@link Page} of {@link EmployeeWorks} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeWorksQueryService extends QueryService<EmployeeWorks> {

    private final Logger log = LoggerFactory.getLogger(EmployeeWorksQueryService.class);

    private final EmployeeWorksRepository employeeWorksRepository;

    public EmployeeWorksQueryService(EmployeeWorksRepository employeeWorksRepository) {
        this.employeeWorksRepository = employeeWorksRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeWorks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeWorks> findByCriteria(EmployeeWorksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeWorks> specification = createSpecification(criteria);
        return employeeWorksRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeWorks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeWorks> findByCriteria(EmployeeWorksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeWorks> specification = createSpecification(criteria);
        return employeeWorksRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeWorksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeWorks> specification = createSpecification(criteria);
        return employeeWorksRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeWorksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeWorks> createSpecification(EmployeeWorksCriteria criteria) {
        Specification<EmployeeWorks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeWorks_.id));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), EmployeeWorks_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), EmployeeWorks_.enddate));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), EmployeeWorks_.designation));
            }
            if (criteria.getLeavingreason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeavingreason(), EmployeeWorks_.leavingreason));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeWorks_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeWorks_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeWorks_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyId(),
                            root -> root.join(EmployeeWorks_.company, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
