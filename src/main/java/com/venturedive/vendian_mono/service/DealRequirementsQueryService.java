package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.repository.DealRequirementsRepository;
import com.venturedive.vendian_mono.service.criteria.DealRequirementsCriteria;
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
 * Service for executing complex queries for {@link DealRequirements} entities in the database.
 * The main input is a {@link DealRequirementsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealRequirements} or a {@link Page} of {@link DealRequirements} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealRequirementsQueryService extends QueryService<DealRequirements> {

    private final Logger log = LoggerFactory.getLogger(DealRequirementsQueryService.class);

    private final DealRequirementsRepository dealRequirementsRepository;

    public DealRequirementsQueryService(DealRequirementsRepository dealRequirementsRepository) {
        this.dealRequirementsRepository = dealRequirementsRepository;
    }

    /**
     * Return a {@link List} of {@link DealRequirements} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealRequirements> findByCriteria(DealRequirementsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealRequirements> specification = createSpecification(criteria);
        return dealRequirementsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealRequirements} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealRequirements> findByCriteria(DealRequirementsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealRequirements> specification = createSpecification(criteria);
        return dealRequirementsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealRequirementsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealRequirements> specification = createSpecification(criteria);
        return dealRequirementsRepository.count(specification);
    }

    /**
     * Function to convert {@link DealRequirementsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealRequirements> createSpecification(DealRequirementsCriteria criteria) {
        Specification<DealRequirements> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealRequirements_.id));
            }
            if (criteria.getDealreqidentifier() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDealreqidentifier(), DealRequirements_.dealreqidentifier));
            }
            if (criteria.getCompetencyname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompetencyname(), DealRequirements_.competencyname));
            }
            if (criteria.getSkills() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkills(), DealRequirements_.skills));
            }
            if (criteria.getResourcerequired() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getResourcerequired(), DealRequirements_.resourcerequired));
            }
            if (criteria.getMinexperiencelevel() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMinexperiencelevel(), DealRequirements_.minexperiencelevel));
            }
            if (criteria.getMaxexperiencelevel() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMaxexperiencelevel(), DealRequirements_.maxexperiencelevel));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealRequirements_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), DealRequirements_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), DealRequirements_.deletedat));
            }
            if (criteria.getDealId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDealId(), root -> root.join(DealRequirements_.deal, JoinType.LEFT).get(Deals_.id))
                    );
            }
            if (criteria.getDealrequirementmatchingresourcesDealrequirementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementmatchingresourcesDealrequirementId(),
                            root ->
                                root
                                    .join(DealRequirements_.dealrequirementmatchingresourcesDealrequirements, JoinType.LEFT)
                                    .get(DealRequirementMatchingResources_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
