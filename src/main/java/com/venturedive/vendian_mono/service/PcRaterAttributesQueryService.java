package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.repository.PcRaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.PcRaterAttributesCriteria;
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
 * Service for executing complex queries for {@link PcRaterAttributes} entities in the database.
 * The main input is a {@link PcRaterAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcRaterAttributes} or a {@link Page} of {@link PcRaterAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcRaterAttributesQueryService extends QueryService<PcRaterAttributes> {

    private final Logger log = LoggerFactory.getLogger(PcRaterAttributesQueryService.class);

    private final PcRaterAttributesRepository pcRaterAttributesRepository;

    public PcRaterAttributesQueryService(PcRaterAttributesRepository pcRaterAttributesRepository) {
        this.pcRaterAttributesRepository = pcRaterAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link PcRaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcRaterAttributes> findByCriteria(PcRaterAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcRaterAttributes> specification = createSpecification(criteria);
        return pcRaterAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcRaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRaterAttributes> findByCriteria(PcRaterAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcRaterAttributes> specification = createSpecification(criteria);
        return pcRaterAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcRaterAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcRaterAttributes> specification = createSpecification(criteria);
        return pcRaterAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link PcRaterAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcRaterAttributes> createSpecification(PcRaterAttributesCriteria criteria) {
        Specification<PcRaterAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcRaterAttributes_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcRaterAttributes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcRaterAttributes_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), PcRaterAttributes_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcRaterAttributes_.version));
            }
            if (criteria.getRatingAttributeMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingAttributeMappingId(),
                            root -> root.join(PcRaterAttributes_.ratingAttributeMapping, JoinType.LEFT).get(RatingAttributeMappings_.id)
                        )
                    );
            }
            if (criteria.getRatingOptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingOptionId(),
                            root -> root.join(PcRaterAttributes_.ratingOption, JoinType.LEFT).get(RatingOptions_.id)
                        )
                    );
            }
            if (criteria.getRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingId(),
                            root -> root.join(PcRaterAttributes_.rating, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
