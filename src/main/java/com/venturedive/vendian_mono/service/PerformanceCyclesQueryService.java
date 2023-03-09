package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PerformanceCycles;
import com.venturedive.vendian_mono.repository.PerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.criteria.PerformanceCyclesCriteria;
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
 * Service for executing complex queries for {@link PerformanceCycles} entities in the database.
 * The main input is a {@link PerformanceCyclesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceCycles} or a {@link Page} of {@link PerformanceCycles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceCyclesQueryService extends QueryService<PerformanceCycles> {

    private final Logger log = LoggerFactory.getLogger(PerformanceCyclesQueryService.class);

    private final PerformanceCyclesRepository performanceCyclesRepository;

    public PerformanceCyclesQueryService(PerformanceCyclesRepository performanceCyclesRepository) {
        this.performanceCyclesRepository = performanceCyclesRepository;
    }

    /**
     * Return a {@link List} of {@link PerformanceCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceCycles> findByCriteria(PerformanceCyclesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceCycles> specification = createSpecification(criteria);
        return performanceCyclesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PerformanceCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycles> findByCriteria(PerformanceCyclesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceCycles> specification = createSpecification(criteria);
        return performanceCyclesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceCyclesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceCycles> specification = createSpecification(criteria);
        return performanceCyclesRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceCyclesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceCycles> createSpecification(PerformanceCyclesCriteria criteria) {
        Specification<PerformanceCycles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceCycles_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), PerformanceCycles_.month));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildSpecification(criteria.getYear(), PerformanceCycles_.year));
            }
            if (criteria.getTotalresources() != null) {
                specification = specification.and(buildSpecification(criteria.getTotalresources(), PerformanceCycles_.totalresources));
            }
            if (criteria.getPmreviewed() != null) {
                specification = specification.and(buildSpecification(criteria.getPmreviewed(), PerformanceCycles_.pmreviewed));
            }
            if (criteria.getArchreviewed() != null) {
                specification = specification.and(buildSpecification(criteria.getArchreviewed(), PerformanceCycles_.archreviewed));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), PerformanceCycles_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), PerformanceCycles_.enddate));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), PerformanceCycles_.isactive));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), PerformanceCycles_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), PerformanceCycles_.updatedat));
            }
            if (criteria.getProjectcount() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectcount(), PerformanceCycles_.projectcount));
            }
            if (criteria.getCriteria() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCriteria(), PerformanceCycles_.criteria));
            }
            if (criteria.getNotificationsent() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificationsent(), PerformanceCycles_.notificationsent));
            }
            if (criteria.getDuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuedate(), PerformanceCycles_.duedate));
            }
            if (criteria.getInitiationdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInitiationdate(), PerformanceCycles_.initiationdate));
            }
            if (criteria.getProjectcycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectcycleId(),
                            root -> root.join(PerformanceCycles_.projectcycles, JoinType.LEFT).get(ProjectCycles_.id)
                        )
                    );
            }
            if (criteria.getEmployeeratingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeratingId(),
                            root -> root.join(PerformanceCycles_.employeeratings, JoinType.LEFT).get(Ratings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
