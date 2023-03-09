package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcStatuses;
import com.venturedive.vendian_mono.repository.PcStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.PcStatusesCriteria;
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
 * Service for executing complex queries for {@link PcStatuses} entities in the database.
 * The main input is a {@link PcStatusesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcStatuses} or a {@link Page} of {@link PcStatuses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcStatusesQueryService extends QueryService<PcStatuses> {

    private final Logger log = LoggerFactory.getLogger(PcStatusesQueryService.class);

    private final PcStatusesRepository pcStatusesRepository;

    public PcStatusesQueryService(PcStatusesRepository pcStatusesRepository) {
        this.pcStatusesRepository = pcStatusesRepository;
    }

    /**
     * Return a {@link List} of {@link PcStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcStatuses> findByCriteria(PcStatusesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcStatuses> specification = createSpecification(criteria);
        return pcStatusesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcStatuses> findByCriteria(PcStatusesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcStatuses> specification = createSpecification(criteria);
        return pcStatusesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcStatusesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcStatuses> specification = createSpecification(criteria);
        return pcStatusesRepository.count(specification);
    }

    /**
     * Function to convert {@link PcStatusesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcStatuses> createSpecification(PcStatusesCriteria criteria) {
        Specification<PcStatuses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcStatuses_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PcStatuses_.name));
            }
            if (criteria.getGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroup(), PcStatuses_.group));
            }
            if (criteria.getSystemKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSystemKey(), PcStatuses_.systemKey));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcStatuses_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcStatuses_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), PcStatuses_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcStatuses_.version));
            }
            if (criteria.getPcratingsStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcratingsStatusId(),
                            root -> root.join(PcStatuses_.pcratingsStatuses, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
