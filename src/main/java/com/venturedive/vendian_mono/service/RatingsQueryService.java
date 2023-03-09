package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.RatingsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingsCriteria;
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
 * Service for executing complex queries for {@link Ratings} entities in the database.
 * The main input is a {@link RatingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ratings} or a {@link Page} of {@link Ratings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RatingsQueryService extends QueryService<Ratings> {

    private final Logger log = LoggerFactory.getLogger(RatingsQueryService.class);

    private final RatingsRepository ratingsRepository;

    public RatingsQueryService(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    /**
     * Return a {@link List} of {@link Ratings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ratings> findByCriteria(RatingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ratings> specification = createSpecification(criteria);
        return ratingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ratings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ratings> findByCriteria(RatingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ratings> specification = createSpecification(criteria);
        return ratingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RatingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ratings> specification = createSpecification(criteria);
        return ratingsRepository.count(specification);
    }

    /**
     * Function to convert {@link RatingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ratings> createSpecification(RatingsCriteria criteria) {
        Specification<Ratings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ratings_.id));
            }
            if (criteria.getRateeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateeid(), Ratings_.rateeid));
            }
            if (criteria.getRateetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateetype(), Ratings_.rateetype));
            }
            if (criteria.getDuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuedate(), Ratings_.duedate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Ratings_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Ratings_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Ratings_.deletedat));
            }
            if (criteria.getRaterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRaterId(), root -> root.join(Ratings_.rater, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getRatingattributesRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingattributesRatingId(),
                            root -> root.join(Ratings_.ratingattributesRatings, JoinType.LEFT).get(RatingAttributes_.id)
                        )
                    );
            }
            if (criteria.getPerformancecycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleId(),
                            root -> root.join(Ratings_.performancecycles, JoinType.LEFT).get(PerformanceCycles_.id)
                        )
                    );
            }
            if (criteria.getProjectcycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcycleId(),
                            root -> root.join(Ratings_.projectcycles, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
