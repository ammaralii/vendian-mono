package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.UserGoalsRepository;
import com.venturedive.vendian_mono.service.criteria.UserGoalsCriteria;
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
 * Service for executing complex queries for {@link UserGoals} entities in the database.
 * The main input is a {@link UserGoalsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserGoals} or a {@link Page} of {@link UserGoals} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserGoalsQueryService extends QueryService<UserGoals> {

    private final Logger log = LoggerFactory.getLogger(UserGoalsQueryService.class);

    private final UserGoalsRepository userGoalsRepository;

    public UserGoalsQueryService(UserGoalsRepository userGoalsRepository) {
        this.userGoalsRepository = userGoalsRepository;
    }

    /**
     * Return a {@link List} of {@link UserGoals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGoals> findByCriteria(UserGoalsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserGoals> specification = createSpecification(criteria);
        return userGoalsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserGoals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGoals> findByCriteria(UserGoalsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserGoals> specification = createSpecification(criteria);
        return userGoalsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserGoalsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserGoals> specification = createSpecification(criteria);
        return userGoalsRepository.count(specification);
    }

    /**
     * Function to convert {@link UserGoalsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserGoals> createSpecification(UserGoalsCriteria criteria) {
        Specification<UserGoals> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserGoals_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), UserGoals_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), UserGoals_.description));
            }
            if (criteria.getMeasurement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeasurement(), UserGoals_.measurement));
            }
            if (criteria.getWeightage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightage(), UserGoals_.weightage));
            }
            if (criteria.getProgress() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProgress(), UserGoals_.progress));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), UserGoals_.status));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), UserGoals_.dueDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserGoals_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserGoals_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), UserGoals_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserGoals_.version));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserGoals_.user, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getReferenceGoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenceGoalId(),
                            root -> root.join(UserGoals_.referenceGoal, JoinType.LEFT).get(Goals_.id)
                        )
                    );
            }
            if (criteria.getUsergoalraterattributesUsergoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsergoalraterattributesUsergoalId(),
                            root -> root.join(UserGoals_.usergoalraterattributesUsergoals, JoinType.LEFT).get(UserGoalRaterAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
