package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeTalents;
import com.venturedive.vendian_mono.repository.EmployeeTalentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeTalentsCriteria;
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
 * Service for executing complex queries for {@link EmployeeTalents} entities in the database.
 * The main input is a {@link EmployeeTalentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeTalents} or a {@link Page} of {@link EmployeeTalents} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeTalentsQueryService extends QueryService<EmployeeTalents> {

    private final Logger log = LoggerFactory.getLogger(EmployeeTalentsQueryService.class);

    private final EmployeeTalentsRepository employeeTalentsRepository;

    public EmployeeTalentsQueryService(EmployeeTalentsRepository employeeTalentsRepository) {
        this.employeeTalentsRepository = employeeTalentsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeTalents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeTalents> findByCriteria(EmployeeTalentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeTalents> specification = createSpecification(criteria);
        return employeeTalentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeTalents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeTalents> findByCriteria(EmployeeTalentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeTalents> specification = createSpecification(criteria);
        return employeeTalentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeTalentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeTalents> specification = createSpecification(criteria);
        return employeeTalentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeTalentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeTalents> createSpecification(EmployeeTalentsCriteria criteria) {
        Specification<EmployeeTalents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeTalents_.id));
            }
            if (criteria.getCriticalposition() != null) {
                specification = specification.and(buildSpecification(criteria.getCriticalposition(), EmployeeTalents_.criticalposition));
            }
            if (criteria.getHighpotential() != null) {
                specification = specification.and(buildSpecification(criteria.getHighpotential(), EmployeeTalents_.highpotential));
            }
            if (criteria.getSuccessorfor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuccessorfor(), EmployeeTalents_.successorfor));
            }
            if (criteria.getCriticalexperience() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getCriticalexperience(), EmployeeTalents_.criticalexperience));
            }
            if (criteria.getPromotionreadiness() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPromotionreadiness(), EmployeeTalents_.promotionreadiness));
            }
            if (criteria.getLeadershipqualities() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLeadershipqualities(), EmployeeTalents_.leadershipqualities));
            }
            if (criteria.getCareerambitions() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCareerambitions(), EmployeeTalents_.careerambitions));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeTalents_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeTalents_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeTalents_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
