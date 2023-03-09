package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ProjectCycles;
import com.venturedive.vendian_mono.repository.ProjectCyclesRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectCyclesCriteria;
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
 * Service for executing complex queries for {@link ProjectCycles} entities in the database.
 * The main input is a {@link ProjectCyclesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCycles} or a {@link Page} of {@link ProjectCycles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCyclesQueryService extends QueryService<ProjectCycles> {

    private final Logger log = LoggerFactory.getLogger(ProjectCyclesQueryService.class);

    private final ProjectCyclesRepository projectCyclesRepository;

    public ProjectCyclesQueryService(ProjectCyclesRepository projectCyclesRepository) {
        this.projectCyclesRepository = projectCyclesRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCycles> findByCriteria(ProjectCyclesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCycles> specification = createSpecification(criteria);
        return projectCyclesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCycles> findByCriteria(ProjectCyclesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCycles> specification = createSpecification(criteria);
        return projectCyclesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCyclesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCycles> specification = createSpecification(criteria);
        return projectCyclesRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCyclesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectCycles> createSpecification(ProjectCyclesCriteria criteria) {
        Specification<ProjectCycles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectCycles_.id));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), ProjectCycles_.isactive));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ProjectCycles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ProjectCycles_.updatedat));
            }
            if (criteria.getAllowedafterduedateat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAllowedafterduedateat(), ProjectCycles_.allowedafterduedateat));
            }
            if (criteria.getDuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuedate(), ProjectCycles_.duedate));
            }
            if (criteria.getAuditlogs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditlogs(), ProjectCycles_.auditlogs));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), ProjectCycles_.deletedat));
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(ProjectCycles_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getAllowedafterduedatebyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAllowedafterduedatebyId(),
                            root -> root.join(ProjectCycles_.allowedafterduedateby, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getArchitectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getArchitectId(),
                            root -> root.join(ProjectCycles_.architect, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectmanagerId(),
                            root -> root.join(ProjectCycles_.projectmanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingId(),
                            root -> root.join(ProjectCycles_.ratings, JoinType.LEFT).get(Ratings_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectratingsProjectcycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectratingsProjectcycleId(),
                            root ->
                                root.join(ProjectCycles_.employeeprojectratingsProjectcycles, JoinType.LEFT).get(EmployeeProjectRatings_.id)
                        )
                    );
            }
            if (criteria.getPerformancecycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleId(),
                            root -> root.join(ProjectCycles_.performancecycles, JoinType.LEFT).get(PerformanceCycles_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
