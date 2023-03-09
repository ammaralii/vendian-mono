package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.GoalGroups;
import com.venturedive.vendian_mono.repository.GoalGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalGroupsCriteria;
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
 * Service for executing complex queries for {@link GoalGroups} entities in the database.
 * The main input is a {@link GoalGroupsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GoalGroups} or a {@link Page} of {@link GoalGroups} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GoalGroupsQueryService extends QueryService<GoalGroups> {

    private final Logger log = LoggerFactory.getLogger(GoalGroupsQueryService.class);

    private final GoalGroupsRepository goalGroupsRepository;

    public GoalGroupsQueryService(GoalGroupsRepository goalGroupsRepository) {
        this.goalGroupsRepository = goalGroupsRepository;
    }

    /**
     * Return a {@link List} of {@link GoalGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GoalGroups> findByCriteria(GoalGroupsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GoalGroups> specification = createSpecification(criteria);
        return goalGroupsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GoalGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GoalGroups> findByCriteria(GoalGroupsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GoalGroups> specification = createSpecification(criteria);
        return goalGroupsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GoalGroupsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GoalGroups> specification = createSpecification(criteria);
        return goalGroupsRepository.count(specification);
    }

    /**
     * Function to convert {@link GoalGroupsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GoalGroups> createSpecification(GoalGroupsCriteria criteria) {
        Specification<GoalGroups> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GoalGroups_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), GoalGroups_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), GoalGroups_.description));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), GoalGroups_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), GoalGroups_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), GoalGroups_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), GoalGroups_.version));
            }
            if (criteria.getGoalgroupmappingsGoalgroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGoalgroupmappingsGoalgroupId(),
                            root -> root.join(GoalGroups_.goalgroupmappingsGoalgroups, JoinType.LEFT).get(GoalGroupMappings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
