package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.repository.UserRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.UserRatingsCriteria;
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
 * Service for executing complex queries for {@link UserRatings} entities in the database.
 * The main input is a {@link UserRatingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRatings} or a {@link Page} of {@link UserRatings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRatingsQueryService extends QueryService<UserRatings> {

    private final Logger log = LoggerFactory.getLogger(UserRatingsQueryService.class);

    private final UserRatingsRepository userRatingsRepository;

    public UserRatingsQueryService(UserRatingsRepository userRatingsRepository) {
        this.userRatingsRepository = userRatingsRepository;
    }

    /**
     * Return a {@link List} of {@link UserRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRatings> findByCriteria(UserRatingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRatings> specification = createSpecification(criteria);
        return userRatingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRatings> findByCriteria(UserRatingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRatings> specification = createSpecification(criteria);
        return userRatingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRatingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRatings> specification = createSpecification(criteria);
        return userRatingsRepository.count(specification);
    }

    /**
     * Function to convert {@link UserRatingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserRatings> createSpecification(UserRatingsCriteria criteria) {
        Specification<UserRatings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserRatings_.id));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRating(), UserRatings_.rating));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), UserRatings_.comment));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserRatings_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserRatings_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), UserRatings_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserRatings_.version));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserRatings_.user, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getReviewManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReviewManagerId(),
                            root -> root.join(UserRatings_.reviewManager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getPerformanceCycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformanceCycleId(),
                            root -> root.join(UserRatings_.performanceCycle, JoinType.LEFT).get(HrPerformanceCycles_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
