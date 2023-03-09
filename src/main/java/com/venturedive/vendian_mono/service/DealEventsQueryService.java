package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DealEvents;
import com.venturedive.vendian_mono.repository.DealEventsRepository;
import com.venturedive.vendian_mono.service.criteria.DealEventsCriteria;
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
 * Service for executing complex queries for {@link DealEvents} entities in the database.
 * The main input is a {@link DealEventsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealEvents} or a {@link Page} of {@link DealEvents} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealEventsQueryService extends QueryService<DealEvents> {

    private final Logger log = LoggerFactory.getLogger(DealEventsQueryService.class);

    private final DealEventsRepository dealEventsRepository;

    public DealEventsQueryService(DealEventsRepository dealEventsRepository) {
        this.dealEventsRepository = dealEventsRepository;
    }

    /**
     * Return a {@link List} of {@link DealEvents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealEvents> findByCriteria(DealEventsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealEvents> specification = createSpecification(criteria);
        return dealEventsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealEvents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealEvents> findByCriteria(DealEventsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealEvents> specification = createSpecification(criteria);
        return dealEventsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealEventsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealEvents> specification = createSpecification(criteria);
        return dealEventsRepository.count(specification);
    }

    /**
     * Function to convert {@link DealEventsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealEvents> createSpecification(DealEventsCriteria criteria) {
        Specification<DealEvents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealEvents_.id));
            }
            if (criteria.getEventtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventtype(), DealEvents_.eventtype));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DealEvents_.createdat));
            }
        }
        return specification;
    }
}
