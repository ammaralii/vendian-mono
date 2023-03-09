package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealResourceStatus;
import com.venturedive.vendian_mono.repository.DealResourceStatusRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourceStatusCriteria;
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
 * Service for executing complex queries for {@link DealResourceStatus} entities in the database.
 * The main input is a {@link DealResourceStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealResourceStatus} or a {@link Page} of {@link DealResourceStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealResourceStatusQueryService extends QueryService<DealResourceStatus> {

    private final Logger log = LoggerFactory.getLogger(DealResourceStatusQueryService.class);

    private final DealResourceStatusRepository dealResourceStatusRepository;

    public DealResourceStatusQueryService(DealResourceStatusRepository dealResourceStatusRepository) {
        this.dealResourceStatusRepository = dealResourceStatusRepository;
    }

    /**
     * Return a {@link List} of {@link DealResourceStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealResourceStatus> findByCriteria(DealResourceStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealResourceStatus> specification = createSpecification(criteria);
        return dealResourceStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealResourceStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceStatus> findByCriteria(DealResourceStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealResourceStatus> specification = createSpecification(criteria);
        return dealResourceStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealResourceStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealResourceStatus> specification = createSpecification(criteria);
        return dealResourceStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link DealResourceStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealResourceStatus> createSpecification(DealResourceStatusCriteria criteria) {
        Specification<DealResourceStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealResourceStatus_.id));
            }
            if (criteria.getDisplayname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayname(), DealResourceStatus_.displayname));
            }
            if (criteria.getGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroup(), DealResourceStatus_.group));
            }
            if (criteria.getSystemKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSystemKey(), DealResourceStatus_.systemKey));
            }
            if (criteria.getEffectivedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectivedate(), DealResourceStatus_.effectivedate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), DealResourceStatus_.enddate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealResourceStatus_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), DealResourceStatus_.updatedat));
            }
            if (criteria.getDealrequirementmatchingresourcesResourcestatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementmatchingresourcesResourcestatusId(),
                            root ->
                                root
                                    .join(DealResourceStatus_.dealrequirementmatchingresourcesResourcestatuses, JoinType.LEFT)
                                    .get(DealRequirementMatchingResources_.id)
                        )
                    );
            }
            if (criteria.getDealrequirementmatchingresourcesSystemstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementmatchingresourcesSystemstatusId(),
                            root ->
                                root
                                    .join(DealResourceStatus_.dealrequirementmatchingresourcesSystemstatuses, JoinType.LEFT)
                                    .get(DealRequirementMatchingResources_.id)
                        )
                    );
            }
            if (criteria.getDealresourceeventlogsResourcestatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourceeventlogsResourcestatusId(),
                            root ->
                                root
                                    .join(DealResourceStatus_.dealresourceeventlogsResourcestatuses, JoinType.LEFT)
                                    .get(DealResourceEventLogs_.id)
                        )
                    );
            }
            if (criteria.getDealresourceeventlogsSystemstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealresourceeventlogsSystemstatusId(),
                            root ->
                                root
                                    .join(DealResourceStatus_.dealresourceeventlogsSystemstatuses, JoinType.LEFT)
                                    .get(DealResourceEventLogs_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
