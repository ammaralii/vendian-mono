package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.repository.DealRequirementMatchingResourcesRepository;
import com.venturedive.vendian_mono.service.criteria.DealRequirementMatchingResourcesCriteria;
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
 * Service for executing complex queries for {@link DealRequirementMatchingResources} entities in the database.
 * The main input is a {@link DealRequirementMatchingResourcesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealRequirementMatchingResources} or a {@link Page} of {@link DealRequirementMatchingResources} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealRequirementMatchingResourcesQueryService extends QueryService<DealRequirementMatchingResources> {

    private final Logger log = LoggerFactory.getLogger(DealRequirementMatchingResourcesQueryService.class);

    private final DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository;

    public DealRequirementMatchingResourcesQueryService(
        DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository
    ) {
        this.dealRequirementMatchingResourcesRepository = dealRequirementMatchingResourcesRepository;
    }

    /**
     * Return a {@link List} of {@link DealRequirementMatchingResources} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealRequirementMatchingResources> findByCriteria(DealRequirementMatchingResourcesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealRequirementMatchingResources> specification = createSpecification(criteria);
        return dealRequirementMatchingResourcesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealRequirementMatchingResources} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealRequirementMatchingResources> findByCriteria(DealRequirementMatchingResourcesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealRequirementMatchingResources> specification = createSpecification(criteria);
        return dealRequirementMatchingResourcesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealRequirementMatchingResourcesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealRequirementMatchingResources> specification = createSpecification(criteria);
        return dealRequirementMatchingResourcesRepository.count(specification);
    }

    /**
     * Function to convert {@link DealRequirementMatchingResourcesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealRequirementMatchingResources> createSpecification(DealRequirementMatchingResourcesCriteria criteria) {
        Specification<DealRequirementMatchingResources> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealRequirementMatchingResources_.id));
            }
            if (criteria.getComments() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getComments(), DealRequirementMatchingResources_.comments));
            }
            if (criteria.getCreatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedat(), DealRequirementMatchingResources_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedat(), DealRequirementMatchingResources_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDeletedat(), DealRequirementMatchingResources_.deletedat));
            }
            if (criteria.getDealrequirementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementId(),
                            root -> root.join(DealRequirementMatchingResources_.dealrequirement, JoinType.LEFT).get(DealRequirements_.id)
                        )
                    );
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(DealRequirementMatchingResources_.resource, JoinType.LEFT).get(DealResources_.id)
                        )
                    );
            }
            if (criteria.getResourcestatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourcestatusId(),
                            root -> root.join(DealRequirementMatchingResources_.resourcestatus, JoinType.LEFT).get(DealResourceStatus_.id)
                        )
                    );
            }
            if (criteria.getSystemstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSystemstatusId(),
                            root -> root.join(DealRequirementMatchingResources_.systemstatus, JoinType.LEFT).get(DealResourceStatus_.id)
                        )
                    );
            }
            if (criteria.getDealresourceeventlogsMatchingresourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourceeventlogsMatchingresourceId(),
                            root ->
                                root
                                    .join(DealRequirementMatchingResources_.dealresourceeventlogsMatchingresources, JoinType.LEFT)
                                    .get(DealResourceEventLogs_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
