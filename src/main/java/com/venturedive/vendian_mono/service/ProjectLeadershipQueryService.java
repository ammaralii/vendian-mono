package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ProjectLeadership;
import com.venturedive.vendian_mono.repository.ProjectLeadershipRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectLeadershipCriteria;
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
 * Service for executing complex queries for {@link ProjectLeadership} entities in the database.
 * The main input is a {@link ProjectLeadershipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectLeadership} or a {@link Page} of {@link ProjectLeadership} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectLeadershipQueryService extends QueryService<ProjectLeadership> {

    private final Logger log = LoggerFactory.getLogger(ProjectLeadershipQueryService.class);

    private final ProjectLeadershipRepository projectLeadershipRepository;

    public ProjectLeadershipQueryService(ProjectLeadershipRepository projectLeadershipRepository) {
        this.projectLeadershipRepository = projectLeadershipRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectLeadership} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectLeadership> findByCriteria(ProjectLeadershipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectLeadership> specification = createSpecification(criteria);
        return projectLeadershipRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectLeadership} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectLeadership> findByCriteria(ProjectLeadershipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectLeadership> specification = createSpecification(criteria);
        return projectLeadershipRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectLeadershipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectLeadership> specification = createSpecification(criteria);
        return projectLeadershipRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectLeadershipCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectLeadership> createSpecification(ProjectLeadershipCriteria criteria) {
        Specification<ProjectLeadership> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectLeadership_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ProjectLeadership_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ProjectLeadership_.updatedat));
            }
            if (criteria.getProjectroleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectroleId(),
                            root -> root.join(ProjectLeadership_.projectrole, JoinType.LEFT).get(ProjectRoles_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(ProjectLeadership_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ProjectLeadership_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
