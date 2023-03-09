package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.repository.ClaimRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimRequestsCriteria;
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
 * Service for executing complex queries for {@link ClaimRequests} entities in the database.
 * The main input is a {@link ClaimRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimRequests} or a {@link Page} of {@link ClaimRequests} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimRequestsQueryService extends QueryService<ClaimRequests> {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestsQueryService.class);

    private final ClaimRequestsRepository claimRequestsRepository;

    public ClaimRequestsQueryService(ClaimRequestsRepository claimRequestsRepository) {
        this.claimRequestsRepository = claimRequestsRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimRequests> findByCriteria(ClaimRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimRequests> specification = createSpecification(criteria);
        return claimRequestsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimRequests> findByCriteria(ClaimRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimRequests> specification = createSpecification(criteria);
        return claimRequestsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimRequests> specification = createSpecification(criteria);
        return claimRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimRequests> createSpecification(ClaimRequestsCriteria criteria) {
        Specification<ClaimRequests> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimRequests_.id));
            }
            if (criteria.getProjectid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectid(), ClaimRequests_.projectid));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ClaimRequests_.comments));
            }
            if (criteria.getAmountreleased() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountreleased(), ClaimRequests_.amountreleased));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), ClaimRequests_.designation));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), ClaimRequests_.department));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), ClaimRequests_.location));
            }
            if (criteria.getManager() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManager(), ClaimRequests_.manager));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimRequests_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimRequests_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ClaimRequests_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getClaimapproversClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimapproversClaimrequestId(),
                            root -> root.join(ClaimRequests_.claimapproversClaimrequests, JoinType.LEFT).get(ClaimApprovers_.id)
                        )
                    );
            }
            if (criteria.getClaimdetailsClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimdetailsClaimrequestId(),
                            root -> root.join(ClaimRequests_.claimdetailsClaimrequests, JoinType.LEFT).get(ClaimDetails_.id)
                        )
                    );
            }
            if (criteria.getClaimimagesClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimimagesClaimrequestId(),
                            root -> root.join(ClaimRequests_.claimimagesClaimrequests, JoinType.LEFT).get(ClaimImages_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
