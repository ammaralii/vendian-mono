package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.repository.PerformanceCycleEmployeeRatingRepository;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycleEmployeeRatingCriteria;
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
 * Service for executing complex queries for {@link PerformanceCycleEmployeeRating} entities in the database.
 * The main input is a {@link PerformanceCycleEmployeeRatingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceCycleEmployeeRating} or a {@link Page} of {@link PerformanceCycleEmployeeRating} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceCycleEmployeeRatingQueryService extends QueryService<PerformanceCycleEmployeeRating> {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycleEmployeeRatingQueryService.class);

    private final PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository;

    public PerformanceCycleEmployeeRatingQueryService(PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository) {
        this.performanceCycleEmployeeRatingRepository = performanceCycleEmployeeRatingRepository;
    }

    /**
     * Return a {@link List} of {@link PerformanceCycleEmployeeRating} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceCycleEmployeeRating> findByCriteria(PerformanceCycleEmployeeRatingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceCycleEmployeeRating> specification = createSpecification(criteria);
        return performanceCycleEmployeeRatingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PerformanceCycleEmployeeRating} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycleEmployeeRating> findByCriteria(PerformanceCycleEmployeeRatingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceCycleEmployeeRating> specification = createSpecification(criteria);
        return performanceCycleEmployeeRatingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceCycleEmployeeRatingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceCycleEmployeeRating> specification = createSpecification(criteria);
        return performanceCycleEmployeeRatingRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceCycleEmployeeRatingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceCycleEmployeeRating> createSpecification(PerformanceCycleEmployeeRatingCriteria criteria) {
        Specification<PerformanceCycleEmployeeRating> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceCycleEmployeeRating_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedAt(), PerformanceCycleEmployeeRating_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PerformanceCycleEmployeeRating_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDeletedAt(), PerformanceCycleEmployeeRating_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PerformanceCycleEmployeeRating_.version));
            }
            if (criteria.getPerformancecycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleId(),
                            root -> root.join(PerformanceCycleEmployeeRating_.performancecycle, JoinType.LEFT).get(HrPerformanceCycles_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(PerformanceCycleEmployeeRating_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getManagerId(),
                            root -> root.join(PerformanceCycleEmployeeRating_.manager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getPcratingsPcemployeeratingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingsPcemployeeratingId(),
                            root -> root.join(PerformanceCycleEmployeeRating_.pcratingsPcemployeeratings, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
            if (criteria.getUserratingsremarksPcemployeeratingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsremarksPcemployeeratingId(),
                            root ->
                                root
                                    .join(PerformanceCycleEmployeeRating_.userratingsremarksPcemployeeratings, JoinType.LEFT)
                                    .get(UserRatingsRemarks_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
