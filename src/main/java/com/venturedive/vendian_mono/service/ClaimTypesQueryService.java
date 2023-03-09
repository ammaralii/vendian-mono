package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimTypes;
import com.venturedive.vendian_mono.repository.ClaimTypesRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimTypesCriteria;
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
 * Service for executing complex queries for {@link ClaimTypes} entities in the database.
 * The main input is a {@link ClaimTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimTypes} or a {@link Page} of {@link ClaimTypes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimTypesQueryService extends QueryService<ClaimTypes> {

    private final Logger log = LoggerFactory.getLogger(ClaimTypesQueryService.class);

    private final ClaimTypesRepository claimTypesRepository;

    public ClaimTypesQueryService(ClaimTypesRepository claimTypesRepository) {
        this.claimTypesRepository = claimTypesRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimTypes> findByCriteria(ClaimTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimTypes> specification = createSpecification(criteria);
        return claimTypesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimTypes> findByCriteria(ClaimTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimTypes> specification = createSpecification(criteria);
        return claimTypesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimTypes> specification = createSpecification(criteria);
        return claimTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimTypes> createSpecification(ClaimTypesCriteria criteria) {
        Specification<ClaimTypes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimTypes_.id));
            }
            if (criteria.getClaimtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClaimtype(), ClaimTypes_.claimtype));
            }
            if (criteria.getDaterangerequired() != null) {
                specification = specification.and(buildSpecification(criteria.getDaterangerequired(), ClaimTypes_.daterangerequired));
            }
            if (criteria.getDescriptionrequired() != null) {
                specification = specification.and(buildSpecification(criteria.getDescriptionrequired(), ClaimTypes_.descriptionrequired));
            }
            if (criteria.getParentid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentid(), ClaimTypes_.parentid));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimTypes_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimTypes_.updatedat));
            }
            if (criteria.getClaimdetailsClaimtypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimdetailsClaimtypeId(),
                            root -> root.join(ClaimTypes_.claimdetailsClaimtypes, JoinType.LEFT).get(ClaimDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
