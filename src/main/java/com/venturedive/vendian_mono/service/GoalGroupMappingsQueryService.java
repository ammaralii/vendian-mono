package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import com.venturedive.vendian_mono.repository.GoalGroupMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalGroupMappingsCriteria;
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
 * Service for executing complex queries for {@link GoalGroupMappings} entities in the database.
 * The main input is a {@link GoalGroupMappingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GoalGroupMappings} or a {@link Page} of {@link GoalGroupMappings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GoalGroupMappingsQueryService extends QueryService<GoalGroupMappings> {

    private final Logger log = LoggerFactory.getLogger(GoalGroupMappingsQueryService.class);

    private final GoalGroupMappingsRepository goalGroupMappingsRepository;

    public GoalGroupMappingsQueryService(GoalGroupMappingsRepository goalGroupMappingsRepository) {
        this.goalGroupMappingsRepository = goalGroupMappingsRepository;
    }

    /**
     * Return a {@link List} of {@link GoalGroupMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GoalGroupMappings> findByCriteria(GoalGroupMappingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GoalGroupMappings> specification = createSpecification(criteria);
        return goalGroupMappingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GoalGroupMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GoalGroupMappings> findByCriteria(GoalGroupMappingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GoalGroupMappings> specification = createSpecification(criteria);
        return goalGroupMappingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GoalGroupMappingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GoalGroupMappings> specification = createSpecification(criteria);
        return goalGroupMappingsRepository.count(specification);
    }

    /**
     * Function to convert {@link GoalGroupMappingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GoalGroupMappings> createSpecification(GoalGroupMappingsCriteria criteria) {
        Specification<GoalGroupMappings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GoalGroupMappings_.id));
            }
            if (criteria.getWeightage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightage(), GoalGroupMappings_.weightage));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), GoalGroupMappings_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), GoalGroupMappings_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), GoalGroupMappings_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), GoalGroupMappings_.version));
            }
            if (criteria.getGoalGroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGoalGroupId(),
                            root -> root.join(GoalGroupMappings_.goalGroup, JoinType.LEFT).get(GoalGroups_.id)
                        )
                    );
            }
            if (criteria.getGoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getGoalId(), root -> root.join(GoalGroupMappings_.goal, JoinType.LEFT).get(Goals_.id))
                    );
            }
        }
        return specification;
    }
}
