package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealResourceSkills;
import com.venturedive.vendian_mono.repository.DealResourceSkillsRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourceSkillsCriteria;
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
 * Service for executing complex queries for {@link DealResourceSkills} entities in the database.
 * The main input is a {@link DealResourceSkillsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealResourceSkills} or a {@link Page} of {@link DealResourceSkills} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealResourceSkillsQueryService extends QueryService<DealResourceSkills> {

    private final Logger log = LoggerFactory.getLogger(DealResourceSkillsQueryService.class);

    private final DealResourceSkillsRepository dealResourceSkillsRepository;

    public DealResourceSkillsQueryService(DealResourceSkillsRepository dealResourceSkillsRepository) {
        this.dealResourceSkillsRepository = dealResourceSkillsRepository;
    }

    /**
     * Return a {@link List} of {@link DealResourceSkills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealResourceSkills> findByCriteria(DealResourceSkillsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealResourceSkills> specification = createSpecification(criteria);
        return dealResourceSkillsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealResourceSkills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceSkills> findByCriteria(DealResourceSkillsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealResourceSkills> specification = createSpecification(criteria);
        return dealResourceSkillsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealResourceSkillsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealResourceSkills> specification = createSpecification(criteria);
        return dealResourceSkillsRepository.count(specification);
    }

    /**
     * Function to convert {@link DealResourceSkillsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealResourceSkills> createSpecification(DealResourceSkillsCriteria criteria) {
        Specification<DealResourceSkills> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealResourceSkills_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealResourceSkills_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), DealResourceSkills_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), DealResourceSkills_.deletedat));
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(DealResourceSkills_.resource, JoinType.LEFT).get(DealResources_.id)
                        )
                    );
            }
            if (criteria.getSkillId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSkillId(),
                            root -> root.join(DealResourceSkills_.skill, JoinType.LEFT).get(Skills_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
