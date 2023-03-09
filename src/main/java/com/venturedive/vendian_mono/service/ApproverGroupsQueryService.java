package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ApproverGroups;
import com.venturedive.vendian_mono.repository.ApproverGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.ApproverGroupsCriteria;
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
 * Service for executing complex queries for {@link ApproverGroups} entities in the database.
 * The main input is a {@link ApproverGroupsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApproverGroups} or a {@link Page} of {@link ApproverGroups} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApproverGroupsQueryService extends QueryService<ApproverGroups> {

    private final Logger log = LoggerFactory.getLogger(ApproverGroupsQueryService.class);

    private final ApproverGroupsRepository approverGroupsRepository;

    public ApproverGroupsQueryService(ApproverGroupsRepository approverGroupsRepository) {
        this.approverGroupsRepository = approverGroupsRepository;
    }

    /**
     * Return a {@link List} of {@link ApproverGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApproverGroups> findByCriteria(ApproverGroupsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApproverGroups> specification = createSpecification(criteria);
        return approverGroupsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApproverGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverGroups> findByCriteria(ApproverGroupsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApproverGroups> specification = createSpecification(criteria);
        return approverGroupsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApproverGroupsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApproverGroups> specification = createSpecification(criteria);
        return approverGroupsRepository.count(specification);
    }

    /**
     * Function to convert {@link ApproverGroupsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApproverGroups> createSpecification(ApproverGroupsCriteria criteria) {
        Specification<ApproverGroups> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApproverGroups_.id));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), ApproverGroups_.referenceId));
            }
            if (criteria.getReferenceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceType(), ApproverGroups_.referenceType));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ApproverGroups_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), ApproverGroups_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), ApproverGroups_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), ApproverGroups_.version));
            }
            if (criteria.getApproversApprovergroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getApproversApprovergroupId(),
                            root -> root.join(ApproverGroups_.approversApprovergroups, JoinType.LEFT).get(Approvers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
