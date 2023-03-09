package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeProjectRoles;
import com.venturedive.vendian_mono.repository.EmployeeProjectRolesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRolesCriteria;
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
 * Service for executing complex queries for {@link EmployeeProjectRoles} entities in the database.
 * The main input is a {@link EmployeeProjectRolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeProjectRoles} or a {@link Page} of {@link EmployeeProjectRoles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeProjectRolesQueryService extends QueryService<EmployeeProjectRoles> {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRolesQueryService.class);

    private final EmployeeProjectRolesRepository employeeProjectRolesRepository;

    public EmployeeProjectRolesQueryService(EmployeeProjectRolesRepository employeeProjectRolesRepository) {
        this.employeeProjectRolesRepository = employeeProjectRolesRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProjectRoles> findByCriteria(EmployeeProjectRolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeProjectRoles> specification = createSpecification(criteria);
        return employeeProjectRolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRoles> findByCriteria(EmployeeProjectRolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeProjectRoles> specification = createSpecification(criteria);
        return employeeProjectRolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeProjectRolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeProjectRoles> specification = createSpecification(criteria);
        return employeeProjectRolesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeProjectRolesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeProjectRoles> createSpecification(EmployeeProjectRolesCriteria criteria) {
        Specification<EmployeeProjectRoles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeProjectRoles_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), EmployeeProjectRoles_.status));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeProjectRoles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeProjectRoles_.updatedat));
            }
            if (criteria.getEmployeeprojectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectId(),
                            root -> root.join(EmployeeProjectRoles_.employeeproject, JoinType.LEFT).get(EmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getProjectroleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectroleId(),
                            root -> root.join(EmployeeProjectRoles_.projectrole, JoinType.LEFT).get(ProjectRoles_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
