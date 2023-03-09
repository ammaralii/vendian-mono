package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.MaritalStatuses;
import com.venturedive.vendian_mono.repository.MaritalStatusesRepository;
import com.venturedive.vendian_mono.service.criteria.MaritalStatusesCriteria;
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
 * Service for executing complex queries for {@link MaritalStatuses} entities in the database.
 * The main input is a {@link MaritalStatusesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaritalStatuses} or a {@link Page} of {@link MaritalStatuses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaritalStatusesQueryService extends QueryService<MaritalStatuses> {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusesQueryService.class);

    private final MaritalStatusesRepository maritalStatusesRepository;

    public MaritalStatusesQueryService(MaritalStatusesRepository maritalStatusesRepository) {
        this.maritalStatusesRepository = maritalStatusesRepository;
    }

    /**
     * Return a {@link List} of {@link MaritalStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaritalStatuses> findByCriteria(MaritalStatusesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MaritalStatuses> specification = createSpecification(criteria);
        return maritalStatusesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MaritalStatuses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaritalStatuses> findByCriteria(MaritalStatusesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaritalStatuses> specification = createSpecification(criteria);
        return maritalStatusesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaritalStatusesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MaritalStatuses> specification = createSpecification(criteria);
        return maritalStatusesRepository.count(specification);
    }

    /**
     * Function to convert {@link MaritalStatusesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaritalStatuses> createSpecification(MaritalStatusesCriteria criteria) {
        Specification<MaritalStatuses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MaritalStatuses_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MaritalStatuses_.status));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), MaritalStatuses_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), MaritalStatuses_.updatedat));
            }
        }
        return specification;
    }
}
