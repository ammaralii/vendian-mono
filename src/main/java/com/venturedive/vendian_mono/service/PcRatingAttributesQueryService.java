package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.repository.PcRatingAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCriteria;
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
 * Service for executing complex queries for {@link PcRatingAttributes} entities in the database.
 * The main input is a {@link PcRatingAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcRatingAttributes} or a {@link Page} of {@link PcRatingAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcRatingAttributesQueryService extends QueryService<PcRatingAttributes> {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesQueryService.class);

    private final PcRatingAttributesRepository pcRatingAttributesRepository;

    public PcRatingAttributesQueryService(PcRatingAttributesRepository pcRatingAttributesRepository) {
        this.pcRatingAttributesRepository = pcRatingAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link PcRatingAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcRatingAttributes> findByCriteria(PcRatingAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcRatingAttributes> specification = createSpecification(criteria);
        return pcRatingAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcRatingAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingAttributes> findByCriteria(PcRatingAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcRatingAttributes> specification = createSpecification(criteria);
        return pcRatingAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcRatingAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcRatingAttributes> specification = createSpecification(criteria);
        return pcRatingAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link PcRatingAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcRatingAttributes> createSpecification(PcRatingAttributesCriteria criteria) {
        Specification<PcRatingAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcRatingAttributes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PcRatingAttributes_.name));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), PcRatingAttributes_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcRatingAttributes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcRatingAttributes_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), PcRatingAttributes_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcRatingAttributes_.version));
            }
            if (criteria.getSubCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubCategory(), PcRatingAttributes_.subCategory));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PcRatingAttributes_.description));
            }
            if (criteria.getPcratingattributescategoriesRatingattributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingattributescategoriesRatingattributeId(),
                            root ->
                                root
                                    .join(PcRatingAttributes_.pcratingattributescategoriesRatingattributes, JoinType.LEFT)
                                    .get(PcRatingAttributesCategories_.id)
                        )
                    );
            }
            if (criteria.getRatingattributemappingsRatingattributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingattributemappingsRatingattributeId(),
                            root ->
                                root
                                    .join(PcRatingAttributes_.ratingattributemappingsRatingattributes, JoinType.LEFT)
                                    .get(RatingAttributeMappings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
