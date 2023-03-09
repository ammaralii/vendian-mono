package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ProjectRoles;
import com.venturedive.vendian_mono.repository.ProjectRolesRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectRolesCriteria;
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
 * Service for executing complex queries for {@link ProjectRoles} entities in the database.
 * The main input is a {@link ProjectRolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectRoles} or a {@link Page} of {@link ProjectRoles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectRolesQueryService extends QueryService<ProjectRoles> {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesQueryService.class);

    private final ProjectRolesRepository projectRolesRepository;

    public ProjectRolesQueryService(ProjectRolesRepository projectRolesRepository) {
        this.projectRolesRepository = projectRolesRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectRoles> findByCriteria(ProjectRolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectRoles> findByCriteria(ProjectRolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectRolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectRolesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectRoles> createSpecification(ProjectRolesCriteria criteria) {
        Specification<ProjectRoles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectRoles_.id));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), ProjectRoles_.role));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ProjectRoles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ProjectRoles_.updatedat));
            }
            if (criteria.getIsleadership() != null) {
                specification = specification.and(buildSpecification(criteria.getIsleadership(), ProjectRoles_.isleadership));
            }
            if (criteria.getEmployeeprojectrolesProjectroleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectrolesProjectroleId(),
                            root -> root.join(ProjectRoles_.employeeprojectrolesProjectroles, JoinType.LEFT).get(EmployeeProjectRoles_.id)
                        )
                    );
            }
            if (criteria.getProjectleadershipProjectroleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectleadershipProjectroleId(),
                            root -> root.join(ProjectRoles_.projectleadershipProjectroles, JoinType.LEFT).get(ProjectLeadership_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
