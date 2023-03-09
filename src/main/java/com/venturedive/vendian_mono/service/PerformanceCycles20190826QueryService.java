package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PerformanceCycles20190826;
import com.venturedive.vendian_mono.repository.PerformanceCycles20190826Repository;
import com.venturedive.vendian_mono.service.criteria.PerformanceCycles20190826Criteria;
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
 * Service for executing complex queries for {@link PerformanceCycles20190826} entities in the database.
 * The main input is a {@link PerformanceCycles20190826Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceCycles20190826} or a {@link Page} of {@link PerformanceCycles20190826} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceCycles20190826QueryService extends QueryService<PerformanceCycles20190826> {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycles20190826QueryService.class);

    private final PerformanceCycles20190826Repository performanceCycles20190826Repository;

    public PerformanceCycles20190826QueryService(PerformanceCycles20190826Repository performanceCycles20190826Repository) {
        this.performanceCycles20190826Repository = performanceCycles20190826Repository;
    }

    /**
     * Return a {@link List} of {@link PerformanceCycles20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceCycles20190826> findByCriteria(PerformanceCycles20190826Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceCycles20190826> specification = createSpecification(criteria);
        return performanceCycles20190826Repository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PerformanceCycles20190826} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycles20190826> findByCriteria(PerformanceCycles20190826Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceCycles20190826> specification = createSpecification(criteria);
        return performanceCycles20190826Repository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceCycles20190826Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceCycles20190826> specification = createSpecification(criteria);
        return performanceCycles20190826Repository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceCycles20190826Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceCycles20190826> createSpecification(PerformanceCycles20190826Criteria criteria) {
        Specification<PerformanceCycles20190826> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceCycles20190826_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), PerformanceCycles20190826_.month));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildSpecification(criteria.getYear(), PerformanceCycles20190826_.year));
            }
            if (criteria.getTotalresources() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTotalresources(), PerformanceCycles20190826_.totalresources));
            }
            if (criteria.getPmreviewed() != null) {
                specification = specification.and(buildSpecification(criteria.getPmreviewed(), PerformanceCycles20190826_.pmreviewed));
            }
            if (criteria.getArchreviewed() != null) {
                specification = specification.and(buildSpecification(criteria.getArchreviewed(), PerformanceCycles20190826_.archreviewed));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), PerformanceCycles20190826_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), PerformanceCycles20190826_.enddate));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), PerformanceCycles20190826_.isactive));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), PerformanceCycles20190826_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), PerformanceCycles20190826_.updatedat));
            }
            if (criteria.getProjectcount() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectcount(), PerformanceCycles20190826_.projectcount));
            }
            if (criteria.getCriteria() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCriteria(), PerformanceCycles20190826_.criteria));
            }
            if (criteria.getNotificationsent() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getNotificationsent(), PerformanceCycles20190826_.notificationsent));
            }
            if (criteria.getDuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuedate(), PerformanceCycles20190826_.duedate));
            }
            if (criteria.getInitiationdate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInitiationdate(), PerformanceCycles20190826_.initiationdate));
            }
        }
        return specification;
    }
}
