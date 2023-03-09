package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Approvers;
import com.venturedive.vendian_mono.repository.ApproversRepository;
import com.venturedive.vendian_mono.service.criteria.ApproversCriteria;
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
 * Service for executing complex queries for {@link Approvers} entities in the database.
 * The main input is a {@link ApproversCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Approvers} or a {@link Page} of {@link Approvers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApproversQueryService extends QueryService<Approvers> {

    private final Logger log = LoggerFactory.getLogger(ApproversQueryService.class);

    private final ApproversRepository approversRepository;

    public ApproversQueryService(ApproversRepository approversRepository) {
        this.approversRepository = approversRepository;
    }

    /**
     * Return a {@link List} of {@link Approvers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Approvers> findByCriteria(ApproversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Approvers> specification = createSpecification(criteria);
        return approversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Approvers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Approvers> findByCriteria(ApproversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Approvers> specification = createSpecification(criteria);
        return approversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApproversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Approvers> specification = createSpecification(criteria);
        return approversRepository.count(specification);
    }

    /**
     * Function to convert {@link ApproversCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Approvers> createSpecification(ApproversCriteria criteria) {
        Specification<Approvers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Approvers_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), Approvers_.userId));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Approvers_.reference));
            }
            if (criteria.getAs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAs(), Approvers_.as));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Approvers_.comment));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Approvers_.status));
            }
            if (criteria.getStausDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStausDate(), Approvers_.stausDate));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), Approvers_.priority));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Approvers_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Approvers_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), Approvers_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Approvers_.version));
            }
            if (criteria.getApproverGroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApproverGroupId(),
                            root -> root.join(Approvers_.approverGroup, JoinType.LEFT).get(ApproverGroups_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
