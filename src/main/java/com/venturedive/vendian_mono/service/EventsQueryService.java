package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Events;
import com.venturedive.vendian_mono.repository.EventsRepository;
import com.venturedive.vendian_mono.service.criteria.EventsCriteria;
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
 * Service for executing complex queries for {@link Events} entities in the database.
 * The main input is a {@link EventsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Events} or a {@link Page} of {@link Events} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventsQueryService extends QueryService<Events> {

    private final Logger log = LoggerFactory.getLogger(EventsQueryService.class);

    private final EventsRepository eventsRepository;

    public EventsQueryService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    /**
     * Return a {@link List} of {@link Events} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Events> findByCriteria(EventsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Events> specification = createSpecification(criteria);
        return eventsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Events} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Events> findByCriteria(EventsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Events> specification = createSpecification(criteria);
        return eventsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Events> specification = createSpecification(criteria);
        return eventsRepository.count(specification);
    }

    /**
     * Function to convert {@link EventsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Events> createSpecification(EventsCriteria criteria) {
        Specification<Events> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Events_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Events_.type));
            }
            if (criteria.getZemployeeprojectsEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getZemployeeprojectsEventId(),
                            root -> root.join(Events_.zemployeeprojectsEvents, JoinType.LEFT).get(ZEmployeeProjects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
