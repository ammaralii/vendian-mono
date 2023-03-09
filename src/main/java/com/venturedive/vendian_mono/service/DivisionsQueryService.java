package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Divisions;
import com.venturedive.vendian_mono.repository.DivisionsRepository;
import com.venturedive.vendian_mono.service.criteria.DivisionsCriteria;
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
 * Service for executing complex queries for {@link Divisions} entities in the database.
 * The main input is a {@link DivisionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Divisions} or a {@link Page} of {@link Divisions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DivisionsQueryService extends QueryService<Divisions> {

    private final Logger log = LoggerFactory.getLogger(DivisionsQueryService.class);

    private final DivisionsRepository divisionsRepository;

    public DivisionsQueryService(DivisionsRepository divisionsRepository) {
        this.divisionsRepository = divisionsRepository;
    }

    /**
     * Return a {@link List} of {@link Divisions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Divisions> findByCriteria(DivisionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Divisions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Divisions> findByCriteria(DivisionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DivisionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsRepository.count(specification);
    }

    /**
     * Function to convert {@link DivisionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Divisions> createSpecification(DivisionsCriteria criteria) {
        Specification<Divisions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Divisions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Divisions_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Divisions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Divisions_.updatedat));
            }
            if (criteria.getDepartmentsDivisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDepartmentsDivisionId(),
                            root -> root.join(Divisions_.departmentsDivisions, JoinType.LEFT).get(Departments_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoDivisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoDivisionId(),
                            root -> root.join(Divisions_.employeejobinfoDivisions, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeesDivisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesDivisionId(),
                            root -> root.join(Divisions_.employeesDivisions, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
