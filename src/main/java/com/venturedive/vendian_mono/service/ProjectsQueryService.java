package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Projects;
import com.venturedive.vendian_mono.repository.ProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.ProjectsCriteria;
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
 * Service for executing complex queries for {@link Projects} entities in the database.
 * The main input is a {@link ProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Projects} or a {@link Page} of {@link Projects} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectsQueryService extends QueryService<Projects> {

    private final Logger log = LoggerFactory.getLogger(ProjectsQueryService.class);

    private final ProjectsRepository projectsRepository;

    public ProjectsQueryService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    /**
     * Return a {@link List} of {@link Projects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Projects> findByCriteria(ProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Projects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Projects> findByCriteria(ProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Projects> specification = createSpecification(criteria);
        return projectsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Projects> createSpecification(ProjectsCriteria criteria) {
        Specification<Projects> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Projects_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Projects_.name));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Projects_.isactive));
            }
            if (criteria.getIsdelete() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdelete(), Projects_.isdelete));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), Projects_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), Projects_.enddate));
            }
            if (criteria.getColorcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColorcode(), Projects_.colorcode));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Projects_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Projects_.updatedat));
            }
            if (criteria.getDeliverymanagerid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliverymanagerid(), Projects_.deliverymanagerid));
            }
            if (criteria.getArchitectid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArchitectid(), Projects_.architectid));
            }
            if (criteria.getIsdeleted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsdeleted(), Projects_.isdeleted));
            }
            if (criteria.getApprovedresources() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovedresources(), Projects_.approvedresources));
            }
            if (criteria.getReleaseat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReleaseat(), Projects_.releaseat));
            }
            if (criteria.getProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectmanagerId(),
                            root -> root.join(Projects_.projectmanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessunitId(),
                            root -> root.join(Projects_.businessunit, JoinType.LEFT).get(BusinessUnits_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectsProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectsProjectId(),
                            root -> root.join(Projects_.employeeprojectsProjects, JoinType.LEFT).get(EmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getInterviewsProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewsProjectId(),
                            root -> root.join(Projects_.interviewsProjects, JoinType.LEFT).get(Interviews_.id)
                        )
                    );
            }
            if (criteria.getProjectcyclesProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcyclesProjectId(),
                            root -> root.join(Projects_.projectcyclesProjects, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
            if (criteria.getProjectleadershipProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectleadershipProjectId(),
                            root -> root.join(Projects_.projectleadershipProjects, JoinType.LEFT).get(ProjectLeadership_.id)
                        )
                    );
            }
            if (criteria.getQuestionsProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsProjectId(),
                            root -> root.join(Projects_.questionsProjects, JoinType.LEFT).get(Questions_.id)
                        )
                    );
            }
            if (criteria.getQuestionsfrequencyperclienttrackProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsfrequencyperclienttrackProjectId(),
                            root ->
                                root
                                    .join(Projects_.questionsfrequencyperclienttrackProjects, JoinType.LEFT)
                                    .get(QuestionsFrequencyPerClientTrack_.id)
                        )
                    );
            }
            if (criteria.getWorklogdetailsProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorklogdetailsProjectId(),
                            root -> root.join(Projects_.worklogdetailsProjects, JoinType.LEFT).get(WorkLogDetails_.id)
                        )
                    );
            }
            if (criteria.getZemployeeprojectsProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsProjectId(),
                            root -> root.join(Projects_.zemployeeprojectsProjects, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
