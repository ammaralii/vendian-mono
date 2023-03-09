package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.repository.DealResourcesRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourcesCriteria;
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
 * Service for executing complex queries for {@link DealResources} entities in the database.
 * The main input is a {@link DealResourcesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealResources} or a {@link Page} of {@link DealResources} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealResourcesQueryService extends QueryService<DealResources> {

    private final Logger log = LoggerFactory.getLogger(DealResourcesQueryService.class);

    private final DealResourcesRepository dealResourcesRepository;

    public DealResourcesQueryService(DealResourcesRepository dealResourcesRepository) {
        this.dealResourcesRepository = dealResourcesRepository;
    }

    /**
     * Return a {@link List} of {@link DealResources} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealResources> findByCriteria(DealResourcesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealResources> specification = createSpecification(criteria);
        return dealResourcesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealResources} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResources> findByCriteria(DealResourcesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealResources> specification = createSpecification(criteria);
        return dealResourcesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealResourcesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealResources> specification = createSpecification(criteria);
        return dealResourcesRepository.count(specification);
    }

    /**
     * Function to convert {@link DealResourcesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealResources> createSpecification(DealResourcesCriteria criteria) {
        Specification<DealResources> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealResources_.id));
            }
            if (criteria.getFirstname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstname(), DealResources_.firstname));
            }
            if (criteria.getLastname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastname(), DealResources_.lastname));
            }
            if (criteria.getJoiningdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoiningdate(), DealResources_.joiningdate));
            }
            if (criteria.getExternalexpyears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExternalexpyears(), DealResources_.externalexpyears));
            }
            if (criteria.getExternalexpmonths() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExternalexpmonths(), DealResources_.externalexpmonths));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealResources_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), DealResources_.updatedat));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), DealResources_.type));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), DealResources_.isactive));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(DealResources_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getDealrequirementmatchingresourcesResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementmatchingresourcesResourceId(),
                            root ->
                                root
                                    .join(DealResources_.dealrequirementmatchingresourcesResources, JoinType.LEFT)
                                    .get(DealRequirementMatchingResources_.id)
                        )
                    );
            }
            if (criteria.getDealresourceskillsResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourceskillsResourceId(),
                            root -> root.join(DealResources_.dealresourceskillsResources, JoinType.LEFT).get(DealResourceSkills_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
