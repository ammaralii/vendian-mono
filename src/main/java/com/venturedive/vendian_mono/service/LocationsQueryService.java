package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Locations;
import com.venturedive.vendian_mono.repository.LocationsRepository;
import com.venturedive.vendian_mono.service.criteria.LocationsCriteria;
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
 * Service for executing complex queries for {@link Locations} entities in the database.
 * The main input is a {@link LocationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Locations} or a {@link Page} of {@link Locations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationsQueryService extends QueryService<Locations> {

    private final Logger log = LoggerFactory.getLogger(LocationsQueryService.class);

    private final LocationsRepository locationsRepository;

    public LocationsQueryService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    /**
     * Return a {@link List} of {@link Locations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Locations> findByCriteria(LocationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Locations> specification = createSpecification(criteria);
        return locationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Locations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Locations> findByCriteria(LocationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Locations> specification = createSpecification(criteria);
        return locationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Locations> specification = createSpecification(criteria);
        return locationsRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Locations> createSpecification(LocationsCriteria criteria) {
        Specification<Locations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Locations_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Locations_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Locations_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Locations_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Locations_.deletedat));
            }
            if (criteria.getEmployeesLocationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesLocationId(),
                            root -> root.join(Locations_.employeesLocations, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
