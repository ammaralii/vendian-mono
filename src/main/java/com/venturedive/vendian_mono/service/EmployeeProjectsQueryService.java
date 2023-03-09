package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeProjects;
import com.venturedive.vendian_mono.repository.EmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectsCriteria;
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
 * Service for executing complex queries for {@link EmployeeProjects} entities in the database.
 * The main input is a {@link EmployeeProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeProjects} or a {@link Page} of {@link EmployeeProjects} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeProjectsQueryService extends QueryService<EmployeeProjects> {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectsQueryService.class);

    private final EmployeeProjectsRepository employeeProjectsRepository;

    public EmployeeProjectsQueryService(EmployeeProjectsRepository employeeProjectsRepository) {
        this.employeeProjectsRepository = employeeProjectsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProjects> findByCriteria(EmployeeProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeProjects> specification = createSpecification(criteria);
        return employeeProjectsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjects> findByCriteria(EmployeeProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeProjects> specification = createSpecification(criteria);
        return employeeProjectsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeProjects> specification = createSpecification(criteria);
        return employeeProjectsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeProjectsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeProjects> createSpecification(EmployeeProjectsCriteria criteria) {
        Specification<EmployeeProjects> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeProjects_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), EmployeeProjects_.status));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmployeeProjects_.type));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), EmployeeProjects_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), EmployeeProjects_.enddate));
            }
            if (criteria.getAllocation() != null) {
                specification = specification.and(buildSpecification(criteria.getAllocation(), EmployeeProjects_.allocation));
            }
            if (criteria.getBilled() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBilled(), EmployeeProjects_.billed));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeProjects_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeProjects_.updatedat));
            }
            if (criteria.getRoleid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoleid(), EmployeeProjects_.roleid));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), EmployeeProjects_.notes));
            }
            if (criteria.getExtendedenddate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtendedenddate(), EmployeeProjects_.extendedenddate));
            }
            if (criteria.getProbability() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProbability(), EmployeeProjects_.probability));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeProjects_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(EmployeeProjects_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getAssignorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignorId(),
                            root -> root.join(EmployeeProjects_.assignor, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectrolesEmployeeprojectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectrolesEmployeeprojectId(),
                            root ->
                                root
                                    .join(EmployeeProjects_.employeeprojectrolesEmployeeprojects, JoinType.LEFT)
                                    .get(EmployeeProjectRoles_.id)
                        )
                    );
            }
            if (criteria.getZemployeeprojectsEmployeeprojectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsEmployeeprojectId(),
                            root -> root.join(EmployeeProjects_.zemployeeprojectsEmployeeprojects, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
