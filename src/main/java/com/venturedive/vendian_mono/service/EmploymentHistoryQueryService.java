package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmploymentHistory;
import com.venturedive.vendian_mono.repository.EmploymentHistoryRepository;
import com.venturedive.vendian_mono.service.criteria.EmploymentHistoryCriteria;
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
 * Service for executing complex queries for {@link EmploymentHistory} entities in the database.
 * The main input is a {@link EmploymentHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmploymentHistory} or a {@link Page} of {@link EmploymentHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmploymentHistoryQueryService extends QueryService<EmploymentHistory> {

    private final Logger log = LoggerFactory.getLogger(EmploymentHistoryQueryService.class);

    private final EmploymentHistoryRepository employmentHistoryRepository;

    public EmploymentHistoryQueryService(EmploymentHistoryRepository employmentHistoryRepository) {
        this.employmentHistoryRepository = employmentHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link EmploymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmploymentHistory> findByCriteria(EmploymentHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmploymentHistory> specification = createSpecification(criteria);
        return employmentHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmploymentHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentHistory> findByCriteria(EmploymentHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmploymentHistory> specification = createSpecification(criteria);
        return employmentHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmploymentHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmploymentHistory> specification = createSpecification(criteria);
        return employmentHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link EmploymentHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmploymentHistory> createSpecification(EmploymentHistoryCriteria criteria) {
        Specification<EmploymentHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmploymentHistory_.id));
            }
            if (criteria.getPositiontitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPositiontitle(), EmploymentHistory_.positiontitle));
            }
            if (criteria.getCompanyname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyname(), EmploymentHistory_.companyname));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), EmploymentHistory_.grade));
            }
            if (criteria.getJobdescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getJobdescription(), EmploymentHistory_.jobdescription));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), EmploymentHistory_.city));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), EmploymentHistory_.country));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), EmploymentHistory_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), EmploymentHistory_.enddate));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmploymentHistory_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmploymentHistory_.updatedat));
            }
            if (criteria.getReasonforleaving() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReasonforleaving(), EmploymentHistory_.reasonforleaving));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmploymentHistory_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
