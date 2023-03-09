package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.repository.EmployeeSkillsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeSkillsCriteria;
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
 * Service for executing complex queries for {@link EmployeeSkills} entities in the database.
 * The main input is a {@link EmployeeSkillsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSkills} or a {@link Page} of {@link EmployeeSkills} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSkillsQueryService extends QueryService<EmployeeSkills> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillsQueryService.class);

    private final EmployeeSkillsRepository employeeSkillsRepository;

    public EmployeeSkillsQueryService(EmployeeSkillsRepository employeeSkillsRepository) {
        this.employeeSkillsRepository = employeeSkillsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeSkills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSkills> findByCriteria(EmployeeSkillsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSkills> specification = createSpecification(criteria);
        return employeeSkillsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeSkills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkills> findByCriteria(EmployeeSkillsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSkills> specification = createSpecification(criteria);
        return employeeSkillsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSkillsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSkills> specification = createSpecification(criteria);
        return employeeSkillsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSkillsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeSkills> createSpecification(EmployeeSkillsCriteria criteria) {
        Specification<EmployeeSkills> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeSkills_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeSkills_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeSkills_.updatedat));
            }
            if (criteria.getExpertise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpertise(), EmployeeSkills_.expertise));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeSkills_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSkillId(), root -> root.join(EmployeeSkills_.skill, JoinType.LEFT).get(Skills_.id))
                    );
            }
        }
        return specification;
    }
}
