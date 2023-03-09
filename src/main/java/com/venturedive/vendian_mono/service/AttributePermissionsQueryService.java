package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.AttributePermissions;
import com.venturedive.vendian_mono.repository.AttributePermissionsRepository;
import com.venturedive.vendian_mono.service.criteria.AttributePermissionsCriteria;
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
 * Service for executing complex queries for {@link AttributePermissions} entities in the database.
 * The main input is a {@link AttributePermissionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttributePermissions} or a {@link Page} of {@link AttributePermissions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttributePermissionsQueryService extends QueryService<AttributePermissions> {

    private final Logger log = LoggerFactory.getLogger(AttributePermissionsQueryService.class);

    private final AttributePermissionsRepository attributePermissionsRepository;

    public AttributePermissionsQueryService(AttributePermissionsRepository attributePermissionsRepository) {
        this.attributePermissionsRepository = attributePermissionsRepository;
    }

    /**
     * Return a {@link List} of {@link AttributePermissions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttributePermissions> findByCriteria(AttributePermissionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AttributePermissions> specification = createSpecification(criteria);
        return attributePermissionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AttributePermissions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttributePermissions> findByCriteria(AttributePermissionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AttributePermissions> specification = createSpecification(criteria);
        return attributePermissionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttributePermissionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AttributePermissions> specification = createSpecification(criteria);
        return attributePermissionsRepository.count(specification);
    }

    /**
     * Function to convert {@link AttributePermissionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AttributePermissions> createSpecification(AttributePermissionsCriteria criteria) {
        Specification<AttributePermissions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AttributePermissions_.id));
            }
            if (criteria.getMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMethod(), AttributePermissions_.method));
            }
            if (criteria.getRoute() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoute(), AttributePermissions_.route));
            }
            if (criteria.getResponsepermissions() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getResponsepermissions(), AttributePermissions_.responsepermissions)
                    );
            }
            if (criteria.getRequestpermissions() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRequestpermissions(), AttributePermissions_.requestpermissions));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), AttributePermissions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), AttributePermissions_.updatedat));
            }
        }
        return specification;
    }
}
