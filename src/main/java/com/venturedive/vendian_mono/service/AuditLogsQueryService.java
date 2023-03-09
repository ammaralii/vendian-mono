package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.AuditLogs;
import com.venturedive.vendian_mono.repository.AuditLogsRepository;
import com.venturedive.vendian_mono.service.criteria.AuditLogsCriteria;
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
 * Service for executing complex queries for {@link AuditLogs} entities in the database.
 * The main input is a {@link AuditLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuditLogs} or a {@link Page} of {@link AuditLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuditLogsQueryService extends QueryService<AuditLogs> {

    private final Logger log = LoggerFactory.getLogger(AuditLogsQueryService.class);

    private final AuditLogsRepository auditLogsRepository;

    public AuditLogsQueryService(AuditLogsRepository auditLogsRepository) {
        this.auditLogsRepository = auditLogsRepository;
    }

    /**
     * Return a {@link List} of {@link AuditLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuditLogs> findByCriteria(AuditLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuditLogs> specification = createSpecification(criteria);
        return auditLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AuditLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditLogs> findByCriteria(AuditLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuditLogs> specification = createSpecification(criteria);
        return auditLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuditLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuditLogs> specification = createSpecification(criteria);
        return auditLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link AuditLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuditLogs> createSpecification(AuditLogsCriteria criteria) {
        Specification<AuditLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuditLogs_.id));
            }
            if (criteria.getEvent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvent(), AuditLogs_.event));
            }
            if (criteria.getEventTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventTime(), AuditLogs_.eventTime));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AuditLogs_.description));
            }
            if (criteria.getOldChange() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOldChange(), AuditLogs_.oldChange));
            }
            if (criteria.getNewChange() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewChange(), AuditLogs_.newChange));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), AuditLogs_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), AuditLogs_.updatedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), AuditLogs_.version));
            }
        }
        return specification;
    }
}
