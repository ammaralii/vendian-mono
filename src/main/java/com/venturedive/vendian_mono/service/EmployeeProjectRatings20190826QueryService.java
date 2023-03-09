package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeProjectRatings20190826;
import com.venturedive.vendian_mono.repository.EmployeeProjectRatings20190826Repository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProjectRatings20190826Criteria;
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
 * Service for executing complex queries for {@link EmployeeProjectRatings20190826} entities in the database.
 * The main input is a {@link EmployeeProjectRatings20190826Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeProjectRatings20190826} or a {@link Page} of {@link EmployeeProjectRatings20190826} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeProjectRatings20190826QueryService extends QueryService<EmployeeProjectRatings20190826> {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectRatings20190826QueryService.class);

    private final EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository;

    public EmployeeProjectRatings20190826QueryService(EmployeeProjectRatings20190826Repository employeeProjectRatings20190826Repository) {
        this.employeeProjectRatings20190826Repository = employeeProjectRatings20190826Repository;
    }

    /**
     * Return a {@link List} of {@link EmployeeProjectRatings20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProjectRatings20190826> findByCriteria(EmployeeProjectRatings20190826Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeProjectRatings20190826> specification = createSpecification(criteria);
        return employeeProjectRatings20190826Repository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeProjectRatings20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProjectRatings20190826> findByCriteria(EmployeeProjectRatings20190826Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeProjectRatings20190826> specification = createSpecification(criteria);
        return employeeProjectRatings20190826Repository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeProjectRatings20190826Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeProjectRatings20190826> specification = createSpecification(criteria);
        return employeeProjectRatings20190826Repository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeProjectRatings20190826Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeProjectRatings20190826> createSpecification(EmployeeProjectRatings20190826Criteria criteria) {
        Specification<EmployeeProjectRatings20190826> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeProjectRatings20190826_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeProjectRatings20190826_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeProjectRatings20190826_.updatedat));
            }
            if (criteria.getProjectarchitectid() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProjectarchitectid(), EmployeeProjectRatings20190826_.projectarchitectid)
                    );
            }
            if (criteria.getProjectmanagerid() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProjectmanagerid(), EmployeeProjectRatings20190826_.projectmanagerid)
                    );
            }
            if (criteria.getEmployeeid() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getEmployeeid(), EmployeeProjectRatings20190826_.employeeid));
            }
            if (criteria.getProjectcycleid() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProjectcycleid(), EmployeeProjectRatings20190826_.projectcycleid)
                    );
            }
        }
        return specification;
    }
}
