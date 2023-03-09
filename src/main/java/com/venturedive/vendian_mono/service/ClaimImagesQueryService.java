package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ClaimImages;
import com.venturedive.vendian_mono.repository.ClaimImagesRepository;
import com.venturedive.vendian_mono.service.criteria.ClaimImagesCriteria;
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
 * Service for executing complex queries for {@link ClaimImages} entities in the database.
 * The main input is a {@link ClaimImagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimImages} or a {@link Page} of {@link ClaimImages} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimImagesQueryService extends QueryService<ClaimImages> {

    private final Logger log = LoggerFactory.getLogger(ClaimImagesQueryService.class);

    private final ClaimImagesRepository claimImagesRepository;

    public ClaimImagesQueryService(ClaimImagesRepository claimImagesRepository) {
        this.claimImagesRepository = claimImagesRepository;
    }

    /**
     * Return a {@link List} of {@link ClaimImages} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimImages> findByCriteria(ClaimImagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClaimImages> specification = createSpecification(criteria);
        return claimImagesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClaimImages} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimImages> findByCriteria(ClaimImagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClaimImages> specification = createSpecification(criteria);
        return claimImagesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimImagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClaimImages> specification = createSpecification(criteria);
        return claimImagesRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimImagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClaimImages> createSpecification(ClaimImagesCriteria criteria) {
        Specification<ClaimImages> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClaimImages_.id));
            }
            if (criteria.getImages() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImages(), ClaimImages_.images));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ClaimImages_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ClaimImages_.updatedat));
            }
            if (criteria.getClaimrequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClaimrequestId(),
                            root -> root.join(ClaimImages_.claimrequest, JoinType.LEFT).get(ClaimRequests_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
