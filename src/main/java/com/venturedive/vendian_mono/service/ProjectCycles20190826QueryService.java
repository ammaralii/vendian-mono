package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ProjectCycles20190826;
import com.venturedive.vendian_mono.repository.ProjectCycles20190826Repository;
import com.venturedive.vendian_mono.service.criteria.ProjectCycles20190826Criteria;
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
 * Service for executing complex queries for {@link ProjectCycles20190826} entities in the database.
 * The main input is a {@link ProjectCycles20190826Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCycles20190826} or a {@link Page} of {@link ProjectCycles20190826} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCycles20190826QueryService extends QueryService<ProjectCycles20190826> {

    private final Logger log = LoggerFactory.getLogger(ProjectCycles20190826QueryService.class);

    private final ProjectCycles20190826Repository projectCycles20190826Repository;

    public ProjectCycles20190826QueryService(ProjectCycles20190826Repository projectCycles20190826Repository) {
        this.projectCycles20190826Repository = projectCycles20190826Repository;
    }

    /**
     * Return a {@link List} of {@link ProjectCycles20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCycles20190826> findByCriteria(ProjectCycles20190826Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCycles20190826> specification = createSpecification(criteria);
        return projectCycles20190826Repository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCycles20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCycles20190826> findByCriteria(ProjectCycles20190826Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCycles20190826> specification = createSpecification(criteria);
        return projectCycles20190826Repository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCycles20190826Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCycles20190826> specification = createSpecification(criteria);
        return projectCycles20190826Repository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCycles20190826Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectCycles20190826> createSpecification(ProjectCycles20190826Criteria criteria) {
        Specification<ProjectCycles20190826> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectCycles20190826_.id));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), ProjectCycles20190826_.isactive));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ProjectCycles20190826_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ProjectCycles20190826_.updatedat));
            }
            if (criteria.getPerformancecycleid() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPerformancecycleid(), ProjectCycles20190826_.performancecycleid));
            }
            if (criteria.getProjectid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectid(), ProjectCycles20190826_.projectid));
            }
            if (criteria.getAllowedafterduedateby() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAllowedafterduedateby(), ProjectCycles20190826_.allowedafterduedateby)
                    );
            }
            if (criteria.getAllowedafterduedateat() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAllowedafterduedateat(), ProjectCycles20190826_.allowedafterduedateat)
                    );
            }
            if (criteria.getDuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuedate(), ProjectCycles20190826_.duedate));
            }
        }
        return specification;
    }
}
