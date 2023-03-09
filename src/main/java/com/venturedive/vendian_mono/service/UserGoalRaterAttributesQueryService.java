package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.repository.UserGoalRaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.UserGoalRaterAttributesCriteria;
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
 * Service for executing complex queries for {@link UserGoalRaterAttributes} entities in the database.
 * The main input is a {@link UserGoalRaterAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserGoalRaterAttributes} or a {@link Page} of {@link UserGoalRaterAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserGoalRaterAttributesQueryService extends QueryService<UserGoalRaterAttributes> {

    private final Logger log = LoggerFactory.getLogger(UserGoalRaterAttributesQueryService.class);

    private final UserGoalRaterAttributesRepository userGoalRaterAttributesRepository;

    public UserGoalRaterAttributesQueryService(UserGoalRaterAttributesRepository userGoalRaterAttributesRepository) {
        this.userGoalRaterAttributesRepository = userGoalRaterAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link UserGoalRaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGoalRaterAttributes> findByCriteria(UserGoalRaterAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserGoalRaterAttributes> specification = createSpecification(criteria);
        return userGoalRaterAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserGoalRaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGoalRaterAttributes> findByCriteria(UserGoalRaterAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserGoalRaterAttributes> specification = createSpecification(criteria);
        return userGoalRaterAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserGoalRaterAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserGoalRaterAttributes> specification = createSpecification(criteria);
        return userGoalRaterAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link UserGoalRaterAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserGoalRaterAttributes> createSpecification(UserGoalRaterAttributesCriteria criteria) {
        Specification<UserGoalRaterAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserGoalRaterAttributes_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserGoalRaterAttributes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserGoalRaterAttributes_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), UserGoalRaterAttributes_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserGoalRaterAttributes_.version));
            }
            if (criteria.getRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingId(),
                            root -> root.join(UserGoalRaterAttributes_.rating, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
            if (criteria.getUsergoalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsergoalId(),
                            root -> root.join(UserGoalRaterAttributes_.usergoal, JoinType.LEFT).get(UserGoals_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
