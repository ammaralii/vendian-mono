package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeBirthdays;
import com.venturedive.vendian_mono.repository.EmployeeBirthdaysRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeBirthdaysCriteria;
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
 * Service for executing complex queries for {@link EmployeeBirthdays} entities in the database.
 * The main input is a {@link EmployeeBirthdaysCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeBirthdays} or a {@link Page} of {@link EmployeeBirthdays} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeBirthdaysQueryService extends QueryService<EmployeeBirthdays> {

    private final Logger log = LoggerFactory.getLogger(EmployeeBirthdaysQueryService.class);

    private final EmployeeBirthdaysRepository employeeBirthdaysRepository;

    public EmployeeBirthdaysQueryService(EmployeeBirthdaysRepository employeeBirthdaysRepository) {
        this.employeeBirthdaysRepository = employeeBirthdaysRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeBirthdays} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeBirthdays> findByCriteria(EmployeeBirthdaysCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeBirthdays> specification = createSpecification(criteria);
        return employeeBirthdaysRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeBirthdays} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeBirthdays> findByCriteria(EmployeeBirthdaysCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeBirthdays> specification = createSpecification(criteria);
        return employeeBirthdaysRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeBirthdaysCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeBirthdays> specification = createSpecification(criteria);
        return employeeBirthdaysRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeBirthdaysCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeBirthdays> createSpecification(EmployeeBirthdaysCriteria criteria) {
        Specification<EmployeeBirthdays> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeBirthdays_.id));
            }
            if (criteria.getBirthdayDetails() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBirthdayDetails(), EmployeeBirthdays_.birthdayDetails));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), EmployeeBirthdays_.year));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeBirthdays_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeBirthdays_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeBirthdays_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
