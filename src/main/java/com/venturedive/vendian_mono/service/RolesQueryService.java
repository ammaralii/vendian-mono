package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.repository.RolesRepository;
import com.venturedive.vendian_mono.service.criteria.RolesCriteria;
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
 * Service for executing complex queries for {@link Roles} entities in the database.
 * The main input is a {@link RolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Roles} or a {@link Page} of {@link Roles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RolesQueryService extends QueryService<Roles> {

    private final Logger log = LoggerFactory.getLogger(RolesQueryService.class);

    private final RolesRepository rolesRepository;

    public RolesQueryService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    /**
     * Return a {@link List} of {@link Roles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Roles> findByCriteria(RolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Roles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Roles> findByCriteria(RolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.count(specification);
    }

    /**
     * Function to convert {@link RolesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Roles> createSpecification(RolesCriteria criteria) {
        Specification<Roles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Roles_.id));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Roles_.role));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Roles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Roles_.updatedat));
            }
            if (criteria.getEmployeerolesRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeerolesRoleId(),
                            root -> root.join(Roles_.employeerolesRoles, JoinType.LEFT).get(EmployeeRoles_.id)
                        )
                    );
            }
            if (criteria.getEmployeesRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesRoleId(),
                            root -> root.join(Roles_.employeesRoles, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getRolepermissionsRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRolepermissionsRoleId(),
                            root -> root.join(Roles_.rolepermissionsRoles, JoinType.LEFT).get(RolePermissions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
