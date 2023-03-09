package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.ZEmployeeProjectsRepository;
import com.venturedive.vendian_mono.service.criteria.ZEmployeeProjectsCriteria;
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
 * Service for executing complex queries for {@link ZEmployeeProjects} entities in the database.
 * The main input is a {@link ZEmployeeProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZEmployeeProjects} or a {@link Page} of {@link ZEmployeeProjects} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZEmployeeProjectsQueryService extends QueryService<ZEmployeeProjects> {

    private final Logger log = LoggerFactory.getLogger(ZEmployeeProjectsQueryService.class);

    private final ZEmployeeProjectsRepository zEmployeeProjectsRepository;

    public ZEmployeeProjectsQueryService(ZEmployeeProjectsRepository zEmployeeProjectsRepository) {
        this.zEmployeeProjectsRepository = zEmployeeProjectsRepository;
    }

    /**
     * Return a {@link List} of {@link ZEmployeeProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZEmployeeProjects> findByCriteria(ZEmployeeProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZEmployeeProjects> specification = createSpecification(criteria);
        return zEmployeeProjectsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ZEmployeeProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZEmployeeProjects> findByCriteria(ZEmployeeProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZEmployeeProjects> specification = createSpecification(criteria);
        return zEmployeeProjectsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZEmployeeProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ZEmployeeProjects> specification = createSpecification(criteria);
        return zEmployeeProjectsRepository.count(specification);
    }

    /**
     * Function to convert {@link ZEmployeeProjectsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ZEmployeeProjects> createSpecification(ZEmployeeProjectsCriteria criteria) {
        Specification<ZEmployeeProjects> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ZEmployeeProjects_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ZEmployeeProjects_.status));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ZEmployeeProjects_.type));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), ZEmployeeProjects_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), ZEmployeeProjects_.enddate));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ZEmployeeProjects_.name));
            }
            if (criteria.getAllocation() != null) {
                specification = specification.and(buildSpecification(criteria.getAllocation(), ZEmployeeProjects_.allocation));
            }
            if (criteria.getBilled() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBilled(), ZEmployeeProjects_.billed));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ZEmployeeProjects_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ZEmployeeProjects_.updatedat));
            }
            if (criteria.getDeliverymanagerid() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDeliverymanagerid(), ZEmployeeProjects_.deliverymanagerid));
            }
            if (criteria.getArchitectid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArchitectid(), ZEmployeeProjects_.architectid));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), ZEmployeeProjects_.notes));
            }
            if (criteria.getPreviousenddate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPreviousenddate(), ZEmployeeProjects_.previousenddate));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getData(), ZEmployeeProjects_.data));
            }
            if (criteria.getExtendedenddate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtendedenddate(), ZEmployeeProjects_.extendedenddate));
            }
            if (criteria.getProbability() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProbability(), ZEmployeeProjects_.probability));
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventId(),
                            root -> root.join(ZEmployeeProjects_.event, JoinType.LEFT).get(Events_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ZEmployeeProjects_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(ZEmployeeProjects_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getEmployeeprojectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeprojectId(),
                            root -> root.join(ZEmployeeProjects_.employeeproject, JoinType.LEFT).get(EmployeeProjects_.id)
                        )
                    );
            }
            if (criteria.getAssignorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignorId(),
                            root -> root.join(ZEmployeeProjects_.assignor, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectmanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectmanagerId(),
                            root -> root.join(ZEmployeeProjects_.projectmanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
