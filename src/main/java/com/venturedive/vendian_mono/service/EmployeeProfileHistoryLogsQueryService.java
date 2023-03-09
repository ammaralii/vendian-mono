package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeProfileHistoryLogs;
import com.venturedive.vendian_mono.repository.EmployeeProfileHistoryLogsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeProfileHistoryLogsCriteria;
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
 * Service for executing complex queries for {@link EmployeeProfileHistoryLogs} entities in the database.
 * The main input is a {@link EmployeeProfileHistoryLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeProfileHistoryLogs} or a {@link Page} of {@link EmployeeProfileHistoryLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeProfileHistoryLogsQueryService extends QueryService<EmployeeProfileHistoryLogs> {

    private final Logger log = LoggerFactory.getLogger(EmployeeProfileHistoryLogsQueryService.class);

    private final EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository;

    public EmployeeProfileHistoryLogsQueryService(EmployeeProfileHistoryLogsRepository employeeProfileHistoryLogsRepository) {
        this.employeeProfileHistoryLogsRepository = employeeProfileHistoryLogsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeProfileHistoryLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProfileHistoryLogs> findByCriteria(EmployeeProfileHistoryLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeProfileHistoryLogs> specification = createSpecification(criteria);
        return employeeProfileHistoryLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeProfileHistoryLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeProfileHistoryLogs> findByCriteria(EmployeeProfileHistoryLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeProfileHistoryLogs> specification = createSpecification(criteria);
        return employeeProfileHistoryLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeProfileHistoryLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeProfileHistoryLogs> specification = createSpecification(criteria);
        return employeeProfileHistoryLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeProfileHistoryLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeProfileHistoryLogs> createSpecification(EmployeeProfileHistoryLogsCriteria criteria) {
        Specification<EmployeeProfileHistoryLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeProfileHistoryLogs_.id));
            }
            if (criteria.getTablename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTablename(), EmployeeProfileHistoryLogs_.tablename));
            }
            if (criteria.getRowid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRowid(), EmployeeProfileHistoryLogs_.rowid));
            }
            if (criteria.getEventtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventtype(), EmployeeProfileHistoryLogs_.eventtype));
            }
            if (criteria.getUpdatedbyid() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedbyid(), EmployeeProfileHistoryLogs_.updatedbyid));
            }
            if (criteria.getActivityid() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getActivityid(), EmployeeProfileHistoryLogs_.activityid));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeProfileHistoryLogs_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeProfileHistoryLogs_.updatedat));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), EmployeeProfileHistoryLogs_.category));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeProfileHistoryLogs_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
