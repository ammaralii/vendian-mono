package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Configurations;
import com.venturedive.vendian_mono.repository.ConfigurationsRepository;
import com.venturedive.vendian_mono.service.criteria.ConfigurationsCriteria;
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
 * Service for executing complex queries for {@link Configurations} entities in the database.
 * The main input is a {@link ConfigurationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Configurations} or a {@link Page} of {@link Configurations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationsQueryService extends QueryService<Configurations> {

    private final Logger log = LoggerFactory.getLogger(ConfigurationsQueryService.class);

    private final ConfigurationsRepository configurationsRepository;

    public ConfigurationsQueryService(ConfigurationsRepository configurationsRepository) {
        this.configurationsRepository = configurationsRepository;
    }

    /**
     * Return a {@link List} of {@link Configurations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Configurations> findByCriteria(ConfigurationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Configurations> specification = createSpecification(criteria);
        return configurationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Configurations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Configurations> findByCriteria(ConfigurationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Configurations> specification = createSpecification(criteria);
        return configurationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigurationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Configurations> specification = createSpecification(criteria);
        return configurationsRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigurationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Configurations> createSpecification(ConfigurationsCriteria criteria) {
        Specification<Configurations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Configurations_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Configurations_.name));
            }
            if (criteria.getGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroup(), Configurations_.group));
            }
            if (criteria.getIntValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIntValue(), Configurations_.intValue));
            }
            if (criteria.getStringValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStringValue(), Configurations_.stringValue));
            }
            if (criteria.getDoubleValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDoubleValue(), Configurations_.doubleValue));
            }
            if (criteria.getDateValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateValue(), Configurations_.dateValue));
            }
            if (criteria.getJsonValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJsonValue(), Configurations_.jsonValue));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Configurations_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Configurations_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), Configurations_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Configurations_.version));
            }
        }
        return specification;
    }
}
