package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.repository.WorkLogDetailsRepository;
import com.venturedive.vendian_mono.service.criteria.WorkLogDetailsCriteria;
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
 * Service for executing complex queries for {@link WorkLogDetails} entities in the database.
 * The main input is a {@link WorkLogDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkLogDetails} or a {@link Page} of {@link WorkLogDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkLogDetailsQueryService extends QueryService<WorkLogDetails> {

    private final Logger log = LoggerFactory.getLogger(WorkLogDetailsQueryService.class);

    private final WorkLogDetailsRepository workLogDetailsRepository;

    public WorkLogDetailsQueryService(WorkLogDetailsRepository workLogDetailsRepository) {
        this.workLogDetailsRepository = workLogDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link WorkLogDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkLogDetails> findByCriteria(WorkLogDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkLogDetails> specification = createSpecification(criteria);
        return workLogDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkLogDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkLogDetails> findByCriteria(WorkLogDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkLogDetails> specification = createSpecification(criteria);
        return workLogDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkLogDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkLogDetails> specification = createSpecification(criteria);
        return workLogDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkLogDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkLogDetails> createSpecification(WorkLogDetailsCriteria criteria) {
        Specification<WorkLogDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkLogDetails_.id));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPercentage(), WorkLogDetails_.percentage));
            }
            if (criteria.getHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHours(), WorkLogDetails_.hours));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), WorkLogDetails_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), WorkLogDetails_.updatedat));
            }
            if (criteria.getWorklogId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorklogId(),
                            root -> root.join(WorkLogDetails_.worklog, JoinType.LEFT).get(WorkLogs_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(WorkLogDetails_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
