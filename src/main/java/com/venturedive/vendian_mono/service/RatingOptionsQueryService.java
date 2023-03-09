package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RatingOptions;
import com.venturedive.vendian_mono.repository.RatingOptionsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingOptionsCriteria;
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
 * Service for executing complex queries for {@link RatingOptions} entities in the database.
 * The main input is a {@link RatingOptionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RatingOptions} or a {@link Page} of {@link RatingOptions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RatingOptionsQueryService extends QueryService<RatingOptions> {

    private final Logger log = LoggerFactory.getLogger(RatingOptionsQueryService.class);

    private final RatingOptionsRepository ratingOptionsRepository;

    public RatingOptionsQueryService(RatingOptionsRepository ratingOptionsRepository) {
        this.ratingOptionsRepository = ratingOptionsRepository;
    }

    /**
     * Return a {@link List} of {@link RatingOptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RatingOptions> findByCriteria(RatingOptionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RatingOptions> specification = createSpecification(criteria);
        return ratingOptionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RatingOptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingOptions> findByCriteria(RatingOptionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RatingOptions> specification = createSpecification(criteria);
        return ratingOptionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RatingOptionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RatingOptions> specification = createSpecification(criteria);
        return ratingOptionsRepository.count(specification);
    }

    /**
     * Function to convert {@link RatingOptionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RatingOptions> createSpecification(RatingOptionsCriteria criteria) {
        Specification<RatingOptions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RatingOptions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RatingOptions_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RatingOptions_.description));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), RatingOptions_.from));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), RatingOptions_.to));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), RatingOptions_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), RatingOptions_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), RatingOptions_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), RatingOptions_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), RatingOptions_.version));
            }
            if (criteria.getPcraterattributesRatingoptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcraterattributesRatingoptionId(),
                            root -> root.join(RatingOptions_.pcraterattributesRatingoptions, JoinType.LEFT).get(PcRaterAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
