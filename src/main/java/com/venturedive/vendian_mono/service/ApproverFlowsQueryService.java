package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ApproverFlows;
import com.venturedive.vendian_mono.repository.ApproverFlowsRepository;
import com.venturedive.vendian_mono.service.criteria.ApproverFlowsCriteria;
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
 * Service for executing complex queries for {@link ApproverFlows} entities in the database.
 * The main input is a {@link ApproverFlowsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApproverFlows} or a {@link Page} of {@link ApproverFlows} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApproverFlowsQueryService extends QueryService<ApproverFlows> {

    private final Logger log = LoggerFactory.getLogger(ApproverFlowsQueryService.class);

    private final ApproverFlowsRepository approverFlowsRepository;

    public ApproverFlowsQueryService(ApproverFlowsRepository approverFlowsRepository) {
        this.approverFlowsRepository = approverFlowsRepository;
    }

    /**
     * Return a {@link List} of {@link ApproverFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApproverFlows> findByCriteria(ApproverFlowsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApproverFlows> specification = createSpecification(criteria);
        return approverFlowsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApproverFlows} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverFlows> findByCriteria(ApproverFlowsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApproverFlows> specification = createSpecification(criteria);
        return approverFlowsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApproverFlowsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApproverFlows> specification = createSpecification(criteria);
        return approverFlowsRepository.count(specification);
    }

    /**
     * Function to convert {@link ApproverFlowsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApproverFlows> createSpecification(ApproverFlowsCriteria criteria) {
        Specification<ApproverFlows> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApproverFlows_.id));
            }
            if (criteria.getReferenceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceType(), ApproverFlows_.referenceType));
            }
            if (criteria.getApproverStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApproverStatus(), ApproverFlows_.approverStatus));
            }
            if (criteria.getApproval() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApproval(), ApproverFlows_.approval));
            }
            if (criteria.getCurrentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentStatus(), ApproverFlows_.currentStatus));
            }
            if (criteria.getNextStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNextStatus(), ApproverFlows_.nextStatus));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), ApproverFlows_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ApproverFlows_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), ApproverFlows_.updatedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApproverFlows_.version));
            }
        }
        return specification;
    }
}
