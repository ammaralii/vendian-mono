package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.BloodGroups;
import com.venturedive.vendian_mono.repository.BloodGroupsRepository;
import com.venturedive.vendian_mono.service.criteria.BloodGroupsCriteria;
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
 * Service for executing complex queries for {@link BloodGroups} entities in the database.
 * The main input is a {@link BloodGroupsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BloodGroups} or a {@link Page} of {@link BloodGroups} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BloodGroupsQueryService extends QueryService<BloodGroups> {

    private final Logger log = LoggerFactory.getLogger(BloodGroupsQueryService.class);

    private final BloodGroupsRepository bloodGroupsRepository;

    public BloodGroupsQueryService(BloodGroupsRepository bloodGroupsRepository) {
        this.bloodGroupsRepository = bloodGroupsRepository;
    }

    /**
     * Return a {@link List} of {@link BloodGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BloodGroups> findByCriteria(BloodGroupsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BloodGroups> specification = createSpecification(criteria);
        return bloodGroupsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BloodGroups} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGroups> findByCriteria(BloodGroupsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BloodGroups> specification = createSpecification(criteria);
        return bloodGroupsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BloodGroupsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BloodGroups> specification = createSpecification(criteria);
        return bloodGroupsRepository.count(specification);
    }

    /**
     * Function to convert {@link BloodGroupsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BloodGroups> createSpecification(BloodGroupsCriteria criteria) {
        Specification<BloodGroups> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BloodGroups_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BloodGroups_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), BloodGroups_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), BloodGroups_.updatedat));
            }
        }
        return specification;
    }
}
