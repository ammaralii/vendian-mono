package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.repository.AttributesRepository;
import com.venturedive.vendian_mono.service.criteria.AttributesCriteria;
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
 * Service for executing complex queries for {@link Attributes} entities in the database.
 * The main input is a {@link AttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Attributes} or a {@link Page} of {@link Attributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttributesQueryService extends QueryService<Attributes> {

    private final Logger log = LoggerFactory.getLogger(AttributesQueryService.class);

    private final AttributesRepository attributesRepository;

    public AttributesQueryService(AttributesRepository attributesRepository) {
        this.attributesRepository = attributesRepository;
    }

    /**
     * Return a {@link List} of {@link Attributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Attributes> findByCriteria(AttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attributes> specification = createSpecification(criteria);
        return attributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Attributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Attributes> findByCriteria(AttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attributes> specification = createSpecification(criteria);
        return attributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attributes> specification = createSpecification(criteria);
        return attributesRepository.count(specification);
    }

    /**
     * Function to convert {@link AttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attributes> createSpecification(AttributesCriteria criteria) {
        Specification<Attributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Attributes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Attributes_.name));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Attributes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Attributes_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Attributes_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Attributes_.version));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), Attributes_.effDate));
            }
            if (criteria.getLeaveapproversAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveapproversAttributeId(),
                            root -> root.join(Attributes_.leaveapproversAttributes, JoinType.LEFT).get(LeaveApprovers_.id)
                        )
                    );
            }
            if (criteria.getLeaveescalationapproversAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveescalationapproversAttributeId(),
                            root ->
                                root.join(Attributes_.leaveescalationapproversAttributes, JoinType.LEFT).get(LeaveEscalationApprovers_.id)
                        )
                    );
            }
            if (criteria.getRaterattributemappingsAttributesforId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterattributemappingsAttributesforId(),
                            root ->
                                root.join(Attributes_.raterattributemappingsAttributesfors, JoinType.LEFT).get(RaterAttributeMappings_.id)
                        )
                    );
            }
            if (criteria.getRaterattributemappingsAttributesbyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterattributemappingsAttributesbyId(),
                            root ->
                                root.join(Attributes_.raterattributemappingsAttributesbies, JoinType.LEFT).get(RaterAttributeMappings_.id)
                        )
                    );
            }
            if (criteria.getRatingattributemappingsAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingattributemappingsAttributeId(),
                            root -> root.join(Attributes_.ratingattributemappingsAttributes, JoinType.LEFT).get(RatingAttributeMappings_.id)
                        )
                    );
            }
            if (criteria.getUserattributeapprovalrulesAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributeapprovalrulesAttributeId(),
                            root ->
                                root
                                    .join(Attributes_.userattributeapprovalrulesAttributes, JoinType.LEFT)
                                    .get(UserAttributeApprovalRules_.id)
                        )
                    );
            }
            if (criteria.getUserattributeescalationrulesAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributeescalationrulesAttributeId(),
                            root ->
                                root
                                    .join(Attributes_.userattributeescalationrulesAttributes, JoinType.LEFT)
                                    .get(UserAttributeEscalationRules_.id)
                        )
                    );
            }
            if (criteria.getUserattributesAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserattributesAttributeId(),
                            root -> root.join(Attributes_.userattributesAttributes, JoinType.LEFT).get(UserAttributes_.id)
                        )
                    );
            }
            if (criteria.getUserrelationapprovalrulesAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserrelationapprovalrulesAttributeId(),
                            root ->
                                root.join(Attributes_.userrelationapprovalrulesAttributes, JoinType.LEFT).get(UserRelationApprovalRules_.id)
                        )
                    );
            }
            if (criteria.getUserrelationsAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserrelationsAttributeId(),
                            root -> root.join(Attributes_.userrelationsAttributes, JoinType.LEFT).get(UserRelations_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
