package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Sequelizedata;
import com.venturedive.vendian_mono.repository.SequelizedataRepository;
import com.venturedive.vendian_mono.service.criteria.SequelizedataCriteria;
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
 * Service for executing complex queries for {@link Sequelizedata} entities in the database.
 * The main input is a {@link SequelizedataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Sequelizedata} or a {@link Page} of {@link Sequelizedata} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequelizedataQueryService extends QueryService<Sequelizedata> {

    private final Logger log = LoggerFactory.getLogger(SequelizedataQueryService.class);

    private final SequelizedataRepository sequelizedataRepository;

    public SequelizedataQueryService(SequelizedataRepository sequelizedataRepository) {
        this.sequelizedataRepository = sequelizedataRepository;
    }

    /**
     * Return a {@link List} of {@link Sequelizedata} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Sequelizedata> findByCriteria(SequelizedataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sequelizedata> specification = createSpecification(criteria);
        return sequelizedataRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Sequelizedata} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Sequelizedata> findByCriteria(SequelizedataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sequelizedata> specification = createSpecification(criteria);
        return sequelizedataRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequelizedataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sequelizedata> specification = createSpecification(criteria);
        return sequelizedataRepository.count(specification);
    }

    /**
     * Function to convert {@link SequelizedataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sequelizedata> createSpecification(SequelizedataCriteria criteria) {
        Specification<Sequelizedata> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Sequelizedata_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Sequelizedata_.name));
            }
        }
        return specification;
    }
}
