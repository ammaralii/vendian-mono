package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RaterAttributeMappings;
import com.venturedive.vendian_mono.repository.RaterAttributeMappingsRepository;
import com.venturedive.vendian_mono.service.criteria.RaterAttributeMappingsCriteria;
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
 * Service for executing complex queries for {@link RaterAttributeMappings} entities in the database.
 * The main input is a {@link RaterAttributeMappingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RaterAttributeMappings} or a {@link Page} of {@link RaterAttributeMappings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RaterAttributeMappingsQueryService extends QueryService<RaterAttributeMappings> {

    private final Logger log = LoggerFactory.getLogger(RaterAttributeMappingsQueryService.class);

    private final RaterAttributeMappingsRepository raterAttributeMappingsRepository;

    public RaterAttributeMappingsQueryService(RaterAttributeMappingsRepository raterAttributeMappingsRepository) {
        this.raterAttributeMappingsRepository = raterAttributeMappingsRepository;
    }

    /**
     * Return a {@link List} of {@link RaterAttributeMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RaterAttributeMappings> findByCriteria(RaterAttributeMappingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RaterAttributeMappings> specification = createSpecification(criteria);
        return raterAttributeMappingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RaterAttributeMappings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RaterAttributeMappings> findByCriteria(RaterAttributeMappingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RaterAttributeMappings> specification = createSpecification(criteria);
        return raterAttributeMappingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RaterAttributeMappingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RaterAttributeMappings> specification = createSpecification(criteria);
        return raterAttributeMappingsRepository.count(specification);
    }

    /**
     * Function to convert {@link RaterAttributeMappingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RaterAttributeMappings> createSpecification(RaterAttributeMappingsCriteria criteria) {
        Specification<RaterAttributeMappings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RaterAttributeMappings_.id));
            }
            if (criteria.getEffdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffdate(), RaterAttributeMappings_.effdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), RaterAttributeMappings_.enddate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), RaterAttributeMappings_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), RaterAttributeMappings_.updatedat));
            }
            if (criteria.getRaterattributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterattributeId(),
                            root -> root.join(RaterAttributeMappings_.raterattribute, JoinType.LEFT).get(RaterAttributes_.id)
                        )
                    );
            }
            if (criteria.getAttributesForId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributesForId(),
                            root -> root.join(RaterAttributeMappings_.attributesFor, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getAttributesById() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributesById(),
                            root -> root.join(RaterAttributeMappings_.attributesBy, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getRatingattributesRaterattributemappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingattributesRaterattributemappingId(),
                            root ->
                                root
                                    .join(RaterAttributeMappings_.ratingattributesRaterattributemappings, JoinType.LEFT)
                                    .get(RatingAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
