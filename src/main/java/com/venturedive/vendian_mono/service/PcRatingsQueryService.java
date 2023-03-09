package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.repository.PcRatingsRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingsCriteria;
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
 * Service for executing complex queries for {@link PcRatings} entities in the database.
 * The main input is a {@link PcRatingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcRatings} or a {@link Page} of {@link PcRatings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcRatingsQueryService extends QueryService<PcRatings> {

    private final Logger log = LoggerFactory.getLogger(PcRatingsQueryService.class);

    private final PcRatingsRepository pcRatingsRepository;

    public PcRatingsQueryService(PcRatingsRepository pcRatingsRepository) {
        this.pcRatingsRepository = pcRatingsRepository;
    }

    /**
     * Return a {@link List} of {@link PcRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcRatings> findByCriteria(PcRatingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcRatings> specification = createSpecification(criteria);
        return pcRatingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcRatings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatings> findByCriteria(PcRatingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcRatings> specification = createSpecification(criteria);
        return pcRatingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcRatingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcRatings> specification = createSpecification(criteria);
        return pcRatingsRepository.count(specification);
    }

    /**
     * Function to convert {@link PcRatingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcRatings> createSpecification(PcRatingsCriteria criteria) {
        Specification<PcRatings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcRatings_.id));
            }
            if (criteria.getStausDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStausDate(), PcRatings_.stausDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), PcRatings_.dueDate));
            }
            if (criteria.getFreeze() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeze(), PcRatings_.freeze));
            }
            if (criteria.getIncludeInFinalRating() != null) {
                specification = specification.and(buildSpecification(criteria.getIncludeInFinalRating(), PcRatings_.includeInFinalRating));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcRatings_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcRatings_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), PcRatings_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcRatings_.version));
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStatusId(), root -> root.join(PcRatings_.status, JoinType.LEFT).get(PcStatuses_.id))
                    );
            }
            if (criteria.getPcemployeeratingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcemployeeratingId(),
                            root -> root.join(PcRatings_.pcemployeerating, JoinType.LEFT).get(PerformanceCycleEmployeeRating_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(PcRatings_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getPcraterattributesRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcraterattributesRatingId(),
                            root -> root.join(PcRatings_.pcraterattributesRatings, JoinType.LEFT).get(PcRaterAttributes_.id)
                        )
                    );
            }
            if (criteria.getPcratingstrainingsRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingstrainingsRatingId(),
                            root -> root.join(PcRatings_.pcratingstrainingsRatings, JoinType.LEFT).get(PcRatingsTrainings_.id)
                        )
                    );
            }
            if (criteria.getUsergoalraterattributesRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsergoalraterattributesRatingId(),
                            root -> root.join(PcRatings_.usergoalraterattributesRatings, JoinType.LEFT).get(UserGoalRaterAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
