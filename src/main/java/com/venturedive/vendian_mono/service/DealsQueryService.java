package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Deals;
import com.venturedive.vendian_mono.repository.DealsRepository;
import com.venturedive.vendian_mono.service.criteria.DealsCriteria;
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
 * Service for executing complex queries for {@link Deals} entities in the database.
 * The main input is a {@link DealsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Deals} or a {@link Page} of {@link Deals} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealsQueryService extends QueryService<Deals> {

    private final Logger log = LoggerFactory.getLogger(DealsQueryService.class);

    private final DealsRepository dealsRepository;

    public DealsQueryService(DealsRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    /**
     * Return a {@link List} of {@link Deals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Deals> findByCriteria(DealsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Deals> specification = createSpecification(criteria);
        return dealsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Deals} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Deals> findByCriteria(DealsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Deals> specification = createSpecification(criteria);
        return dealsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Deals> specification = createSpecification(criteria);
        return dealsRepository.count(specification);
    }

    /**
     * Function to convert {@link DealsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Deals> createSpecification(DealsCriteria criteria) {
        Specification<Deals> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Deals_.id));
            }
            if (criteria.getDealnumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealnumber(), Deals_.dealnumber));
            }
            if (criteria.getDealname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealname(), Deals_.dealname));
            }
            if (criteria.getBusinessunit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessunit(), Deals_.businessunit));
            }
            if (criteria.getClientname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientname(), Deals_.clientname));
            }
            if (criteria.getDealowner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealowner(), Deals_.dealowner));
            }
            if (criteria.getProposaltype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProposaltype(), Deals_.proposaltype));
            }
            if (criteria.getProjectid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectid(), Deals_.projectid));
            }
            if (criteria.getExpectedstartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedstartdate(), Deals_.expectedstartdate));
            }
            if (criteria.getStage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStage(), Deals_.stage));
            }
            if (criteria.getProbability() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProbability(), Deals_.probability));
            }
            if (criteria.getProjectduration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectduration(), Deals_.projectduration));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Deals_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Deals_.status));
            }
            if (criteria.getClosedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClosedat(), Deals_.closedat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Deals_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Deals_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Deals_.deletedat));
            }
            if (criteria.getResourcingenteredinvendians() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getResourcingenteredinvendians(), Deals_.resourcingenteredinvendians));
            }
            if (criteria.getDealrequirementsDealId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealrequirementsDealId(),
                            root -> root.join(Deals_.dealrequirementsDeals, JoinType.LEFT).get(DealRequirements_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
