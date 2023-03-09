package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.repository.EmployeeJobInfoRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeJobInfoCriteria;
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
 * Service for executing complex queries for {@link EmployeeJobInfo} entities in the database.
 * The main input is a {@link EmployeeJobInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeJobInfo} or a {@link Page} of {@link EmployeeJobInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeJobInfoQueryService extends QueryService<EmployeeJobInfo> {

    private final Logger log = LoggerFactory.getLogger(EmployeeJobInfoQueryService.class);

    private final EmployeeJobInfoRepository employeeJobInfoRepository;

    public EmployeeJobInfoQueryService(EmployeeJobInfoRepository employeeJobInfoRepository) {
        this.employeeJobInfoRepository = employeeJobInfoRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeJobInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeJobInfo> findByCriteria(EmployeeJobInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeJobInfo> specification = createSpecification(criteria);
        return employeeJobInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeJobInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeJobInfo> findByCriteria(EmployeeJobInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeJobInfo> specification = createSpecification(criteria);
        return employeeJobInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeJobInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeJobInfo> specification = createSpecification(criteria);
        return employeeJobInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeJobInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeJobInfo> createSpecification(EmployeeJobInfoCriteria criteria) {
        Specification<EmployeeJobInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeJobInfo_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), EmployeeJobInfo_.title));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), EmployeeJobInfo_.grade));
            }
            if (criteria.getSubgrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubgrade(), EmployeeJobInfo_.subgrade));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), EmployeeJobInfo_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), EmployeeJobInfo_.enddate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeJobInfo_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeJobInfo_.updatedat));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocation(), EmployeeJobInfo_.location));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeJobInfo_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationId(),
                            root -> root.join(EmployeeJobInfo_.designation, JoinType.LEFT).get(Designations_.id)
                        )
                    );
            }
            if (criteria.getReviewmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReviewmanagerId(),
                            root -> root.join(EmployeeJobInfo_.reviewmanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getManagerId(),
                            root -> root.join(EmployeeJobInfo_.manager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getDepartmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDepartmentId(),
                            root -> root.join(EmployeeJobInfo_.department, JoinType.LEFT).get(Departments_.id)
                        )
                    );
            }
            if (criteria.getEmploymenttypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmploymenttypeId(),
                            root -> root.join(EmployeeJobInfo_.employmenttype, JoinType.LEFT).get(EmploymentTypes_.id)
                        )
                    );
            }
            if (criteria.getJobdescriptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getJobdescriptionId(),
                            root -> root.join(EmployeeJobInfo_.jobdescription, JoinType.LEFT).get(DesignationJobDescriptions_.id)
                        )
                    );
            }
            if (criteria.getDivisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDivisionId(),
                            root -> root.join(EmployeeJobInfo_.division, JoinType.LEFT).get(Divisions_.id)
                        )
                    );
            }
            if (criteria.getBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessunitId(),
                            root -> root.join(EmployeeJobInfo_.businessunit, JoinType.LEFT).get(BusinessUnits_.id)
                        )
                    );
            }
            if (criteria.getCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompetencyId(),
                            root -> root.join(EmployeeJobInfo_.competency, JoinType.LEFT).get(Competencies_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
