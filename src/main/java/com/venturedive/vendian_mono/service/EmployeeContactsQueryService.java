package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeContacts;
import com.venturedive.vendian_mono.repository.EmployeeContactsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeContactsCriteria;
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
 * Service for executing complex queries for {@link EmployeeContacts} entities in the database.
 * The main input is a {@link EmployeeContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeContacts} or a {@link Page} of {@link EmployeeContacts} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeContactsQueryService extends QueryService<EmployeeContacts> {

    private final Logger log = LoggerFactory.getLogger(EmployeeContactsQueryService.class);

    private final EmployeeContactsRepository employeeContactsRepository;

    public EmployeeContactsQueryService(EmployeeContactsRepository employeeContactsRepository) {
        this.employeeContactsRepository = employeeContactsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeContacts> findByCriteria(EmployeeContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeContacts> specification = createSpecification(criteria);
        return employeeContactsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeContacts> findByCriteria(EmployeeContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeContacts> specification = createSpecification(criteria);
        return employeeContactsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeContacts> specification = createSpecification(criteria);
        return employeeContactsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeContacts> createSpecification(EmployeeContactsCriteria criteria) {
        Specification<EmployeeContacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeContacts_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmployeeContacts_.type));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeContacts_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeContacts_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeContacts_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
