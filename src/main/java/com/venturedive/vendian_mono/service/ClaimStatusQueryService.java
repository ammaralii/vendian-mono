package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimStatus;
import com.venturedive.vendian_mono.repository.ClaimStatusRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimStatusCriteria;
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
 * Service for executing complex queries for {@link ClaimStatus} entities in the database.
 * The main input is a {@link ClaimStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimStatus} or a {@link Page} of {@link ClaimStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimStatusQueryService extends QueryService<ClaimStatus> {

    private final Logger log = LoggerFactory.getLogger(ClaimStatusQueryService.class);

    private final ClaimStatusRepository claimStatusRepository;

    public ClaimStatusQueryService(ClaimStatusRepository claimStatusRepository) {
        this.claimStatusRepository = claimStatusRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimStatus> findByCriteria(ClaimStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimStatus> specification = createSpecification(criteria);
        return claimStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimStatus> findByCriteria(ClaimStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimStatus> specification = createSpecification(criteria);
        return claimStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimStatus> specification = createSpecification(criteria);
        return claimStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimStatus> createSpecification(ClaimStatusCriteria criteria) {
        Specification<ClaimStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimStatus_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ClaimStatus_.status));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimStatus_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimStatus_.updatedat));
            }
            if (criteria.getClaimapproversStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimapproversStatusId(),
                            root -> root.join(ClaimStatus_.claimapproversStatuses, JoinType.LEFT).get(ClaimApprovers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
