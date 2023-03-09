package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts;
import com.venturedive.vendian_mono.repository.EmployeeEmergencyContactsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeEmergencyContactsCriteria;
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
 * Service for executing complex queries for {@link EmployeeEmergencyContacts} entities in the database.
 * The main input is a {@link EmployeeEmergencyContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeEmergencyContacts} or a {@link Page} of {@link EmployeeEmergencyContacts} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeEmergencyContactsQueryService extends QueryService<EmployeeEmergencyContacts> {

    private final Logger log = LoggerFactory.getLogger(EmployeeEmergencyContactsQueryService.class);

    private final EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository;

    public EmployeeEmergencyContactsQueryService(EmployeeEmergencyContactsRepository employeeEmergencyContactsRepository) {
        this.employeeEmergencyContactsRepository = employeeEmergencyContactsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeEmergencyContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeEmergencyContacts> findByCriteria(EmployeeEmergencyContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeEmergencyContacts> specification = createSpecification(criteria);
        return employeeEmergencyContactsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeEmergencyContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeEmergencyContacts> findByCriteria(EmployeeEmergencyContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeEmergencyContacts> specification = createSpecification(criteria);
        return employeeEmergencyContactsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeEmergencyContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeEmergencyContacts> specification = createSpecification(criteria);
        return employeeEmergencyContactsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeEmergencyContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeEmergencyContacts> createSpecification(EmployeeEmergencyContactsCriteria criteria) {
        Specification<EmployeeEmergencyContacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeEmergencyContacts_.id));
            }
            if (criteria.getFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullname(), EmployeeEmergencyContacts_.fullname));
            }
            if (criteria.getRelationship() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRelationship(), EmployeeEmergencyContacts_.relationship));
            }
            if (criteria.getContactno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactno(), EmployeeEmergencyContacts_.contactno));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeEmergencyContacts_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeEmergencyContacts_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeEmergencyContacts_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
