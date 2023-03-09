package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.repository.RatingAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.RatingAttributeMappingsCriteria;
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
 * Service for executing complex queries for {@link RatingAttributeMappings} entities in the database.
 * The main input is a {@link RatingAttributeMappingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RatingAttributeMappings} or a {@link Page} of {@link RatingAttributeMappings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RatingAttributeMappingsQueryService extends QueryService<RatingAttributeMappings> {

    private final Logger log = LoggerFactory.getLogger(RatingAttributeMappingsQueryService.class);

    private final RatingAttributeMappingsRepository ratingAttributeMappingsRepository;

    public RatingAttributeMappingsQueryService(RatingAttributeMappingsRepository ratingAttributeMappingsRepository) {
        this.ratingAttributeMappingsRepository = ratingAttributeMappingsRepository;
    }

    /**
     * Return a {@link List} of {@link RatingAttributeMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RatingAttributeMappings> findByCriteria(RatingAttributeMappingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RatingAttributeMappings> specification = createSpecification(criteria);
        return ratingAttributeMappingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RatingAttributeMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingAttributeMappings> findByCriteria(RatingAttributeMappingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RatingAttributeMappings> specification = createSpecification(criteria);
        return ratingAttributeMappingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RatingAttributeMappingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RatingAttributeMappings> specification = createSpecification(criteria);
        return ratingAttributeMappingsRepository.count(specification);
    }

    /**
     * Function to convert {@link RatingAttributeMappingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RatingAttributeMappings> createSpecification(RatingAttributeMappingsCriteria criteria) {
        Specification<RatingAttributeMappings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RatingAttributeMappings_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), RatingAttributeMappings_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), RatingAttributeMappings_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), RatingAttributeMappings_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), RatingAttributeMappings_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), RatingAttributeMappings_.version));
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(RatingAttributeMappings_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getRatingAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingAttributeId(),
                            root -> root.join(RatingAttributeMappings_.ratingAttribute, JoinType.LEFT).get(PcRatingAttributes_.id)
                        )
                    );
            }
            if (criteria.getPcraterattributesRatingattributemappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcraterattributesRatingattributemappingId(),
                            root ->
                                root
                                    .join(RatingAttributeMappings_.pcraterattributesRatingattributemappings, JoinType.LEFT)
                                    .get(PcRaterAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
