package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Religions;
import com.venturedive.vendian_mono.repository.ReligionsRepository;
import com.venturedive.vendian_mono.service.criteria.ReligionsCriteria;
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
 * Service for executing complex queries for {@link Religions} entities in the database.
 * The main input is a {@link ReligionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Religions} or a {@link Page} of {@link Religions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReligionsQueryService extends QueryService<Religions> {

    private final Logger log = LoggerFactory.getLogger(ReligionsQueryService.class);

    private final ReligionsRepository religionsRepository;

    public ReligionsQueryService(ReligionsRepository religionsRepository) {
        this.religionsRepository = religionsRepository;
    }

    /**
     * Return a {@link List} of {@link Religions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Religions> findByCriteria(ReligionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Religions> specification = createSpecification(criteria);
        return religionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Religions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Religions> findByCriteria(ReligionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Religions> specification = createSpecification(criteria);
        return religionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReligionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Religions> specification = createSpecification(criteria);
        return religionsRepository.count(specification);
    }

    /**
     * Function to convert {@link ReligionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Religions> createSpecification(ReligionsCriteria criteria) {
        Specification<Religions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Religions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Religions_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Religions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Religions_.updatedat));
            }
        }
        return specification;
    }
}
