package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.repository.RolePermissionsRepository;
import com.venturedive.vendian_mono.service.criteria.RolePermissionsCriteria;
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
 * Service for executing complex queries for {@link RolePermissions} entities in the database.
 * The main input is a {@link RolePermissionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RolePermissions} or a {@link Page} of {@link RolePermissions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RolePermissionsQueryService extends QueryService<RolePermissions> {

    private final Logger log = LoggerFactory.getLogger(RolePermissionsQueryService.class);

    private final RolePermissionsRepository rolePermissionsRepository;

    public RolePermissionsQueryService(RolePermissionsRepository rolePermissionsRepository) {
        this.rolePermissionsRepository = rolePermissionsRepository;
    }

    /**
     * Return a {@link List} of {@link RolePermissions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RolePermissions> findByCriteria(RolePermissionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RolePermissions> specification = createSpecification(criteria);
        return rolePermissionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RolePermissions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RolePermissions> findByCriteria(RolePermissionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RolePermissions> specification = createSpecification(criteria);
        return rolePermissionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RolePermissionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RolePermissions> specification = createSpecification(criteria);
        return rolePermissionsRepository.count(specification);
    }

    /**
     * Function to convert {@link RolePermissionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RolePermissions> createSpecification(RolePermissionsCriteria criteria) {
        Specification<RolePermissions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RolePermissions_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), RolePermissions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), RolePermissions_.updatedat));
            }
            if (criteria.getRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRoleId(), root -> root.join(RolePermissions_.role, JoinType.LEFT).get(Roles_.id))
                    );
            }
            if (criteria.getPermissionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPermissionId(),
                            root -> root.join(RolePermissions_.permission, JoinType.LEFT).get(Permissions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
