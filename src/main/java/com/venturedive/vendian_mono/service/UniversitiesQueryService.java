package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Universities;
import com.venturedive.vendian_mono.repository.UniversitiesRepository;
import com.venturedive.vendian_mono.service.criteria.UniversitiesCriteria;
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
 * Service for executing complex queries for {@link Universities} entities in the database.
 * The main input is a {@link UniversitiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Universities} or a {@link Page} of {@link Universities} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UniversitiesQueryService extends QueryService<Universities> {

    private final Logger log = LoggerFactory.getLogger(UniversitiesQueryService.class);

    private final UniversitiesRepository universitiesRepository;

    public UniversitiesQueryService(UniversitiesRepository universitiesRepository) {
        this.universitiesRepository = universitiesRepository;
    }

    /**
     * Return a {@link List} of {@link Universities} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Universities> findByCriteria(UniversitiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Universities> specification = createSpecification(criteria);
        return universitiesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Universities} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Universities> findByCriteria(UniversitiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Universities> specification = createSpecification(criteria);
        return universitiesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UniversitiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Universities> specification = createSpecification(criteria);
        return universitiesRepository.count(specification);
    }

    /**
     * Function to convert {@link UniversitiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Universities> createSpecification(UniversitiesCriteria criteria) {
        Specification<Universities> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Universities_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Universities_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Universities_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Universities_.updatedat));
            }
        }
        return specification;
    }
}
