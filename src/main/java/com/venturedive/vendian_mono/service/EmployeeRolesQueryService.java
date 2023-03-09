package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeRoles;
import com.venturedive.vendian_mono.repository.EmployeeRolesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeRolesCriteria;
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
 * Service for executing complex queries for {@link EmployeeRoles} entities in the database.
 * The main input is a {@link EmployeeRolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeRoles} or a {@link Page} of {@link EmployeeRoles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeRolesQueryService extends QueryService<EmployeeRoles> {

    private final Logger log = LoggerFactory.getLogger(EmployeeRolesQueryService.class);

    private final EmployeeRolesRepository employeeRolesRepository;

    public EmployeeRolesQueryService(EmployeeRolesRepository employeeRolesRepository) {
        this.employeeRolesRepository = employeeRolesRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeRoles> findByCriteria(EmployeeRolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeRoles> specification = createSpecification(criteria);
        return employeeRolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeRoles> findByCriteria(EmployeeRolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeRoles> specification = createSpecification(criteria);
        return employeeRolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeRolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeRoles> specification = createSpecification(criteria);
        return employeeRolesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeRolesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeRoles> createSpecification(EmployeeRolesCriteria criteria) {
        Specification<EmployeeRoles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeRoles_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeRoles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeRoles_.updatedat));
            }
            if (criteria.getEmployeeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeid(), EmployeeRoles_.employeeid));
            }
            if (criteria.getRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoleId(), root -> root.join(EmployeeRoles_.role, JoinType.LEFT).get(Roles_.id))
                    );
            }
        }
        return specification;
    }
}
