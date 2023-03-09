package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.WorkLogs;
import com.venturedive.vendian_mono.repository.WorkLogsRepository;
import com.venturedive.vendian_mono.service.criteria.WorkLogsCriteria;
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
 * Service for executing complex queries for {@link WorkLogs} entities in the database.
 * The main input is a {@link WorkLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkLogs} or a {@link Page} of {@link WorkLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkLogsQueryService extends QueryService<WorkLogs> {

    private final Logger log = LoggerFactory.getLogger(WorkLogsQueryService.class);

    private final WorkLogsRepository workLogsRepository;

    public WorkLogsQueryService(WorkLogsRepository workLogsRepository) {
        this.workLogsRepository = workLogsRepository;
    }

    /**
     * Return a {@link List} of {@link WorkLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkLogs> findByCriteria(WorkLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkLogs> specification = createSpecification(criteria);
        return workLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WorkLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkLogs> findByCriteria(WorkLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkLogs> specification = createSpecification(criteria);
        return workLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkLogs> specification = createSpecification(criteria);
        return workLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkLogs> createSpecification(WorkLogsCriteria criteria) {
        Specification<WorkLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkLogs_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), WorkLogs_.date));
            }
            if (criteria.getMood() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMood(), WorkLogs_.mood));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), WorkLogs_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), WorkLogs_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(WorkLogs_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getWorklogdetailsWorklogId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWorklogdetailsWorklogId(),
                            root -> root.join(WorkLogs_.worklogdetailsWorklogs, JoinType.LEFT).get(WorkLogDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
