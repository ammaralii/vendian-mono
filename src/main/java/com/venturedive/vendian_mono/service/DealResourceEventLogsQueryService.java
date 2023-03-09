package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import com.venturedive.vendian_mono.repository.DealResourceEventLogsRepository;
import com.venturedive.vendian_mono.service.criteria.DealResourceEventLogsCriteria;
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
 * Service for executing complex queries for {@link DealResourceEventLogs} entities in the database.
 * The main input is a {@link DealResourceEventLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealResourceEventLogs} or a {@link Page} of {@link DealResourceEventLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealResourceEventLogsQueryService extends QueryService<DealResourceEventLogs> {

    private final Logger log = LoggerFactory.getLogger(DealResourceEventLogsQueryService.class);

    private final DealResourceEventLogsRepository dealResourceEventLogsRepository;

    public DealResourceEventLogsQueryService(DealResourceEventLogsRepository dealResourceEventLogsRepository) {
        this.dealResourceEventLogsRepository = dealResourceEventLogsRepository;
    }

    /**
     * Return a {@link List} of {@link DealResourceEventLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealResourceEventLogs> findByCriteria(DealResourceEventLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealResourceEventLogs> specification = createSpecification(criteria);
        return dealResourceEventLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealResourceEventLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResourceEventLogs> findByCriteria(DealResourceEventLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealResourceEventLogs> specification = createSpecification(criteria);
        return dealResourceEventLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealResourceEventLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealResourceEventLogs> specification = createSpecification(criteria);
        return dealResourceEventLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link DealResourceEventLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealResourceEventLogs> createSpecification(DealResourceEventLogsCriteria criteria) {
        Specification<DealResourceEventLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealResourceEventLogs_.id));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), DealResourceEventLogs_.comments));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealResourceEventLogs_.createdat));
            }
            if (criteria.getMatchingresourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatchingresourceId(),
                            root ->
                                root.join(DealResourceEventLogs_.matchingresource, JoinType.LEFT).get(DealRequirementMatchingResources_.id)
                        )
                    );
            }
            if (criteria.getResourcestatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourcestatusId(),
                            root -> root.join(DealResourceEventLogs_.resourcestatus, JoinType.LEFT).get(DealResourceStatus_.id)
                        )
                    );
            }
            if (criteria.getSystemstatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSystemstatusId(),
                            root -> root.join(DealResourceEventLogs_.systemstatus, JoinType.LEFT).get(DealResourceStatus_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
