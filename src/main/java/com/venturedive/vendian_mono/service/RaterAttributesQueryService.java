package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.RaterAttributes;
import com.venturedive.vendian_mono.repository.RaterAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.RaterAttributesCriteria;
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
 * Service for executing complex queries for {@link RaterAttributes} entities in the database.
 * The main input is a {@link RaterAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RaterAttributes} or a {@link Page} of {@link RaterAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RaterAttributesQueryService extends QueryService<RaterAttributes> {

    private final Logger log = LoggerFactory.getLogger(RaterAttributesQueryService.class);

    private final RaterAttributesRepository raterAttributesRepository;

    public RaterAttributesQueryService(RaterAttributesRepository raterAttributesRepository) {
        this.raterAttributesRepository = raterAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link RaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RaterAttributes> findByCriteria(RaterAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RaterAttributes> specification = createSpecification(criteria);
        return raterAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RaterAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RaterAttributes> findByCriteria(RaterAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RaterAttributes> specification = createSpecification(criteria);
        return raterAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RaterAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RaterAttributes> specification = createSpecification(criteria);
        return raterAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link RaterAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RaterAttributes> createSpecification(RaterAttributesCriteria criteria) {
        Specification<RaterAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RaterAttributes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RaterAttributes_.name));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), RaterAttributes_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RaterAttributes_.description));
            }
            if (criteria.getEffdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffdate(), RaterAttributes_.effdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), RaterAttributes_.enddate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), RaterAttributes_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), RaterAttributes_.updatedat));
            }
            if (criteria.getRaterattributemappingsRaterattributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterattributemappingsRaterattributeId(),
                            root ->
                                root
                                    .join(RaterAttributes_.raterattributemappingsRaterattributes, JoinType.LEFT)
                                    .get(RaterAttributeMappings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
