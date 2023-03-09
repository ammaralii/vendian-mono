package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.repository.RatingAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.RatingAttributesCriteria;
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
 * Service for executing complex queries for {@link RatingAttributes} entities in the database.
 * The main input is a {@link RatingAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RatingAttributes} or a {@link Page} of {@link RatingAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RatingAttributesQueryService extends QueryService<RatingAttributes> {

    private final Logger log = LoggerFactory.getLogger(RatingAttributesQueryService.class);

    private final RatingAttributesRepository ratingAttributesRepository;

    public RatingAttributesQueryService(RatingAttributesRepository ratingAttributesRepository) {
        this.ratingAttributesRepository = ratingAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link RatingAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RatingAttributes> findByCriteria(RatingAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RatingAttributes> specification = createSpecification(criteria);
        return ratingAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RatingAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingAttributes> findByCriteria(RatingAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RatingAttributes> specification = createSpecification(criteria);
        return ratingAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RatingAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RatingAttributes> specification = createSpecification(criteria);
        return ratingAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link RatingAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RatingAttributes> createSpecification(RatingAttributesCriteria criteria) {
        Specification<RatingAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RatingAttributes_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), RatingAttributes_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), RatingAttributes_.updatedat));
            }
            if (criteria.getRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingId(),
                            root -> root.join(RatingAttributes_.rating, JoinType.LEFT).get(Ratings_.id)
                        )
                    );
            }
            if (criteria.getRaterattributemappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterattributemappingId(),
                            root -> root.join(RatingAttributes_.raterattributemapping, JoinType.LEFT).get(RaterAttributeMappings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
