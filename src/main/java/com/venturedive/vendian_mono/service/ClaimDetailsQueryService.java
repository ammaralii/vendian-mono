package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.repository.ClaimDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimDetailsCriteria;
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
 * Service for executing complex queries for {@link ClaimDetails} entities in the database.
 * The main input is a {@link ClaimDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimDetails} or a {@link Page} of {@link ClaimDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimDetailsQueryService extends QueryService<ClaimDetails> {

    private final Logger log = LoggerFactory.getLogger(ClaimDetailsQueryService.class);

    private final ClaimDetailsRepository claimDetailsRepository;

    public ClaimDetailsQueryService(ClaimDetailsRepository claimDetailsRepository) {
        this.claimDetailsRepository = claimDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimDetails> findByCriteria(ClaimDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimDetails> specification = createSpecification(criteria);
        return claimDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimDetails> findByCriteria(ClaimDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimDetails> specification = createSpecification(criteria);
        return claimDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimDetails> specification = createSpecification(criteria);
        return claimDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimDetails> createSpecification(ClaimDetailsCriteria criteria) {
        Specification<ClaimDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimDetails_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), ClaimDetails_.amount));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), ClaimDetails_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), ClaimDetails_.enddate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ClaimDetails_.description));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimDetails_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimDetails_.updatedat));
            }
            if (criteria.getClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimrequestId(),
                            root -> root.join(ClaimDetails_.claimrequest, JoinType.LEFT).get(ClaimRequests_.id)
                        )
                    );
            }
            if (criteria.getClaimtypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimtypeId(),
                            root -> root.join(ClaimDetails_.claimtype, JoinType.LEFT).get(ClaimTypes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
