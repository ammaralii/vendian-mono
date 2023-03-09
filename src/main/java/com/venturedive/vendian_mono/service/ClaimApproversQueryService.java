package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.repository.ClaimApproversRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimApproversCriteria;
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
 * Service for executing complex queries for {@link ClaimApprovers} entities in the database.
 * The main input is a {@link ClaimApproversCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimApprovers} or a {@link Page} of {@link ClaimApprovers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimApproversQueryService extends QueryService<ClaimApprovers> {

    private final Logger log = LoggerFactory.getLogger(ClaimApproversQueryService.class);

    private final ClaimApproversRepository claimApproversRepository;

    public ClaimApproversQueryService(ClaimApproversRepository claimApproversRepository) {
        this.claimApproversRepository = claimApproversRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimApprovers> findByCriteria(ClaimApproversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimApprovers> specification = createSpecification(criteria);
        return claimApproversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimApprovers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimApprovers> findByCriteria(ClaimApproversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimApprovers> specification = createSpecification(criteria);
        return claimApproversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimApproversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimApprovers> specification = createSpecification(criteria);
        return claimApproversRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimApproversCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimApprovers> createSpecification(ClaimApproversCriteria criteria) {
        Specification<ClaimApprovers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimApprovers_.id));
            }
            if (criteria.getReferenceid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceid(), ClaimApprovers_.referenceid));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), ClaimApprovers_.designation));
            }
            if (criteria.getApproveorder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApproveorder(), ClaimApprovers_.approveorder));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), ClaimApprovers_.reference));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ClaimApprovers_.comments));
            }
            if (criteria.getApprovedby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovedby(), ClaimApprovers_.approvedby));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimApprovers_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimApprovers_.updatedat));
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusId(),
                            root -> root.join(ClaimApprovers_.status, JoinType.LEFT).get(ClaimStatus_.id)
                        )
                    );
            }
            if (criteria.getClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimrequestId(),
                            root -> root.join(ClaimApprovers_.claimrequest, JoinType.LEFT).get(ClaimRequests_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
