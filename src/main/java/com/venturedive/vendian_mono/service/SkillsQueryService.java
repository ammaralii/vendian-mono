package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.SkillsRepository;
import com.venturedive.vendian_mono.service.criteria.SkillsCriteria;
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
 * Service for executing complex queries for {@link Skills} entities in the database.
 * The main input is a {@link SkillsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Skills} or a {@link Page} of {@link Skills} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillsQueryService extends QueryService<Skills> {

    private final Logger log = LoggerFactory.getLogger(SkillsQueryService.class);

    private final SkillsRepository skillsRepository;

    public SkillsQueryService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    /**
     * Return a {@link List} of {@link Skills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Skills> findByCriteria(SkillsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Skills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Skills> findByCriteria(SkillsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.count(specification);
    }

    /**
     * Function to convert {@link SkillsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Skills> createSpecification(SkillsCriteria criteria) {
        Specification<Skills> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Skills_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Skills_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Skills_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Skills_.updatedat));
            }
            if (criteria.getDealresourceskillsSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourceskillsSkillId(),
                            root -> root.join(Skills_.dealresourceskillsSkills, JoinType.LEFT).get(DealResourceSkills_.id)
                        )
                    );
            }
            if (criteria.getEmployeeskillsSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeskillsSkillId(),
                            root -> root.join(Skills_.employeeskillsSkills, JoinType.LEFT).get(EmployeeSkills_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
