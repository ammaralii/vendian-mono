package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.repository.BusinessUnitsRepository;
import com.venturedive.vendian_mono.service.criteria.BusinessUnitsCriteria;
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
 * Service for executing complex queries for {@link BusinessUnits} entities in the database.
 * The main input is a {@link BusinessUnitsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessUnits} or a {@link Page} of {@link BusinessUnits} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessUnitsQueryService extends QueryService<BusinessUnits> {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitsQueryService.class);

    private final BusinessUnitsRepository businessUnitsRepository;

    public BusinessUnitsQueryService(BusinessUnitsRepository businessUnitsRepository) {
        this.businessUnitsRepository = businessUnitsRepository;
    }

    /**
     * Return a {@link List} of {@link BusinessUnits} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessUnits> findByCriteria(BusinessUnitsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessUnits> specification = createSpecification(criteria);
        return businessUnitsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BusinessUnits} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessUnits> findByCriteria(BusinessUnitsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessUnits> specification = createSpecification(criteria);
        return businessUnitsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessUnitsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessUnits> specification = createSpecification(criteria);
        return businessUnitsRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessUnitsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessUnits> createSpecification(BusinessUnitsCriteria criteria) {
        Specification<BusinessUnits> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessUnits_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BusinessUnits_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), BusinessUnits_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), BusinessUnits_.updatedat));
            }
            if (criteria.getEmployeejobinfoBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoBusinessunitId(),
                            root -> root.join(BusinessUnits_.employeejobinfoBusinessunits, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeesBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesBusinessunitId(),
                            root -> root.join(BusinessUnits_.employeesBusinessunits, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectsBusinessunitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectsBusinessunitId(),
                            root -> root.join(BusinessUnits_.projectsBusinessunits, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
