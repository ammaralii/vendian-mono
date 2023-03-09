package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeDetails;
import com.venturedive.vendian_mono.repository.EmployeeDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeDetailsCriteria;
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
 * Service for executing complex queries for {@link EmployeeDetails} entities in the database.
 * The main input is a {@link EmployeeDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDetails} or a {@link Page} of {@link EmployeeDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDetailsQueryService extends QueryService<EmployeeDetails> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsQueryService.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetailsQueryService(EmployeeDetailsRepository employeeDetailsRepository) {
        this.employeeDetailsRepository = employeeDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDetails> findByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDetails> findByCriteria(EmployeeDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDetails> createSpecification(EmployeeDetailsCriteria criteria) {
        Specification<EmployeeDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDetails_.id));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReligion(), EmployeeDetails_.religion));
            }
            if (criteria.getMaritalstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalstatus(), EmployeeDetails_.maritalstatus));
            }
            if (criteria.getBloodgroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBloodgroup(), EmployeeDetails_.bloodgroup));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeDetails_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeDetails_.updatedat));
            }
            if (criteria.getTotaltenure() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotaltenure(), EmployeeDetails_.totaltenure));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeDetails_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
