package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.repository.EmploymentTypesRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentTypesCriteria;
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
 * Service for executing complex queries for {@link EmploymentTypes} entities in the database.
 * The main input is a {@link EmploymentTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmploymentTypes} or a {@link Page} of {@link EmploymentTypes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmploymentTypesQueryService extends QueryService<EmploymentTypes> {

    private final Logger log = LoggerFactory.getLogger(EmploymentTypesQueryService.class);

    private final EmploymentTypesRepository employmentTypesRepository;

    public EmploymentTypesQueryService(EmploymentTypesRepository employmentTypesRepository) {
        this.employmentTypesRepository = employmentTypesRepository;
    }

    /**
     * Return a {@link List} of {@link EmploymentTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmploymentTypes> findByCriteria(EmploymentTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmploymentTypes> specification = createSpecification(criteria);
        return employmentTypesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmploymentTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentTypes> findByCriteria(EmploymentTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmploymentTypes> specification = createSpecification(criteria);
        return employmentTypesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmploymentTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmploymentTypes> specification = createSpecification(criteria);
        return employmentTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmploymentTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmploymentTypes> createSpecification(EmploymentTypesCriteria criteria) {
        Specification<EmploymentTypes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmploymentTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmploymentTypes_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmploymentTypes_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmploymentTypes_.updatedat));
            }
            if (criteria.getEmployeejobinfoEmploymenttypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoEmploymenttypeId(),
                            root -> root.join(EmploymentTypes_.employeejobinfoEmploymenttypes, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeesEmploymenttypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesEmploymenttypeId(),
                            root -> root.join(EmploymentTypes_.employeesEmploymenttypes, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
