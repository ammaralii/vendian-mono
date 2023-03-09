package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Sequelizemeta;
import com.venturedive.vendian_mono.repository.SequelizemetaRepository;
import com.venturedive.vendian_mono.service.criteria.SequelizemetaCriteria;
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
 * Service for executing complex queries for {@link Sequelizemeta} entities in the database.
 * The main input is a {@link SequelizemetaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Sequelizemeta} or a {@link Page} of {@link Sequelizemeta} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequelizemetaQueryService extends QueryService<Sequelizemeta> {

    private final Logger log = LoggerFactory.getLogger(SequelizemetaQueryService.class);

    private final SequelizemetaRepository sequelizemetaRepository;

    public SequelizemetaQueryService(SequelizemetaRepository sequelizemetaRepository) {
        this.sequelizemetaRepository = sequelizemetaRepository;
    }

    /**
     * Return a {@link List} of {@link Sequelizemeta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Sequelizemeta> findByCriteria(SequelizemetaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sequelizemeta> specification = createSpecification(criteria);
        return sequelizemetaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Sequelizemeta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Sequelizemeta> findByCriteria(SequelizemetaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sequelizemeta> specification = createSpecification(criteria);
        return sequelizemetaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequelizemetaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sequelizemeta> specification = createSpecification(criteria);
        return sequelizemetaRepository.count(specification);
    }

    /**
     * Function to convert {@link SequelizemetaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sequelizemeta> createSpecification(SequelizemetaCriteria criteria) {
        Specification<Sequelizemeta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Sequelizemeta_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Sequelizemeta_.name));
            }
        }
        return specification;
    }
}
