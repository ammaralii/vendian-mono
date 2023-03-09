package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.repository.GoalsRepository;
import com.venturedive.vendian_mono.service.criteria.GoalsCriteria;
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
 * Service for executing complex queries for {@link Goals} entities in the database.
 * The main input is a {@link GoalsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Goals} or a {@link Page} of {@link Goals} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GoalsQueryService extends QueryService<Goals> {

    private final Logger log = LoggerFactory.getLogger(GoalsQueryService.class);

    private final GoalsRepository goalsRepository;

    public GoalsQueryService(GoalsRepository goalsRepository) {
        this.goalsRepository = goalsRepository;
    }

    /**
     * Return a {@link List} of {@link Goals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Goals> findByCriteria(GoalsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Goals> specification = createSpecification(criteria);
        return goalsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Goals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Goals> findByCriteria(GoalsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Goals> specification = createSpecification(criteria);
        return goalsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GoalsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Goals> specification = createSpecification(criteria);
        return goalsRepository.count(specification);
    }

    /**
     * Function to convert {@link GoalsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Goals> createSpecification(GoalsCriteria criteria) {
        Specification<Goals> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Goals_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Goals_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Goals_.description));
            }
            if (criteria.getMeasurement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeasurement(), Goals_.measurement));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Goals_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Goals_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), Goals_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Goals_.version));
            }
            if (criteria.getGoalgroupmappingsGoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGoalgroupmappingsGoalId(),
                            root -> root.join(Goals_.goalgroupmappingsGoals, JoinType.LEFT).get(GoalGroupMappings_.id)
                        )
                    );
            }
            if (criteria.getUsergoalsReferencegoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsergoalsReferencegoalId(),
                            root -> root.join(Goals_.usergoalsReferencegoals, JoinType.LEFT).get(UserGoals_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
