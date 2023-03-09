package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.repository.PcRatingAttributesCategoriesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingAttributesCategoriesCriteria;
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
 * Service for executing complex queries for {@link PcRatingAttributesCategories} entities in the database.
 * The main input is a {@link PcRatingAttributesCategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcRatingAttributesCategories} or a {@link Page} of {@link PcRatingAttributesCategories} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcRatingAttributesCategoriesQueryService extends QueryService<PcRatingAttributesCategories> {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesCategoriesQueryService.class);

    private final PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository;

    public PcRatingAttributesCategoriesQueryService(PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository) {
        this.pcRatingAttributesCategoriesRepository = pcRatingAttributesCategoriesRepository;
    }

    /**
     * Return a {@link List} of {@link PcRatingAttributesCategories} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcRatingAttributesCategories> findByCriteria(PcRatingAttributesCategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcRatingAttributesCategories> specification = createSpecification(criteria);
        return pcRatingAttributesCategoriesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcRatingAttributesCategories} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingAttributesCategories> findByCriteria(PcRatingAttributesCategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcRatingAttributesCategories> specification = createSpecification(criteria);
        return pcRatingAttributesCategoriesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcRatingAttributesCategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcRatingAttributesCategories> specification = createSpecification(criteria);
        return pcRatingAttributesCategoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link PcRatingAttributesCategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcRatingAttributesCategories> createSpecification(PcRatingAttributesCategoriesCriteria criteria) {
        Specification<PcRatingAttributesCategories> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcRatingAttributesCategories_.id));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), PcRatingAttributesCategories_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcRatingAttributesCategories_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcRatingAttributesCategories_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), PcRatingAttributesCategories_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcRatingAttributesCategories_.version));
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(PcRatingAttributesCategories_.category, JoinType.LEFT).get(RatingCategories_.id)
                        )
                    );
            }
            if (criteria.getRatingAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingAttributeId(),
                            root -> root.join(PcRatingAttributesCategories_.ratingAttribute, JoinType.LEFT).get(PcRatingAttributes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
