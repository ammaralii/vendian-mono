package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.repository.HrPerformanceCyclesRepository;
import com.venturedive.vendian_mono.service.criteria.HrPerformanceCyclesCriteria;
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
 * Service for executing complex queries for {@link HrPerformanceCycles} entities in the database.
 * The main input is a {@link HrPerformanceCyclesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HrPerformanceCycles} or a {@link Page} of {@link HrPerformanceCycles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HrPerformanceCyclesQueryService extends QueryService<HrPerformanceCycles> {

    private final Logger log = LoggerFactory.getLogger(HrPerformanceCyclesQueryService.class);

    private final HrPerformanceCyclesRepository hrPerformanceCyclesRepository;

    public HrPerformanceCyclesQueryService(HrPerformanceCyclesRepository hrPerformanceCyclesRepository) {
        this.hrPerformanceCyclesRepository = hrPerformanceCyclesRepository;
    }

    /**
     * Return a {@link List} of {@link HrPerformanceCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HrPerformanceCycles> findByCriteria(HrPerformanceCyclesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HrPerformanceCycles> specification = createSpecification(criteria);
        return hrPerformanceCyclesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HrPerformanceCycles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HrPerformanceCycles> findByCriteria(HrPerformanceCyclesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HrPerformanceCycles> specification = createSpecification(criteria);
        return hrPerformanceCyclesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HrPerformanceCyclesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HrPerformanceCycles> specification = createSpecification(criteria);
        return hrPerformanceCyclesRepository.count(specification);
    }

    /**
     * Function to convert {@link HrPerformanceCyclesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HrPerformanceCycles> createSpecification(HrPerformanceCyclesCriteria criteria) {
        Specification<HrPerformanceCycles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HrPerformanceCycles_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), HrPerformanceCycles_.title));
            }
            if (criteria.getFreeze() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeze(), HrPerformanceCycles_.freeze));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), HrPerformanceCycles_.dueDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), HrPerformanceCycles_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), HrPerformanceCycles_.endDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), HrPerformanceCycles_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), HrPerformanceCycles_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), HrPerformanceCycles_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), HrPerformanceCycles_.version));
            }
            if (criteria.getPerformancecycleemployeeratingPerformancecycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformancecycleemployeeratingPerformancecycleId(),
                            root ->
                                root
                                    .join(HrPerformanceCycles_.performancecycleemployeeratingPerformancecycles, JoinType.LEFT)
                                    .get(PerformanceCycleEmployeeRating_.id)
                        )
                    );
            }
            if (criteria.getUserratingsPerformancecycleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsPerformancecycleId(),
                            root -> root.join(HrPerformanceCycles_.userratingsPerformancecycles, JoinType.LEFT).get(UserRatings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
