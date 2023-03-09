package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeCompensation;
import com.venturedive.vendian_mono.repository.EmployeeCompensationRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeCompensationCriteria;
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
 * Service for executing complex queries for {@link EmployeeCompensation} entities in the database.
 * The main input is a {@link EmployeeCompensationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeCompensation} or a {@link Page} of {@link EmployeeCompensation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeCompensationQueryService extends QueryService<EmployeeCompensation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeCompensationQueryService.class);

    private final EmployeeCompensationRepository employeeCompensationRepository;

    public EmployeeCompensationQueryService(EmployeeCompensationRepository employeeCompensationRepository) {
        this.employeeCompensationRepository = employeeCompensationRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeCompensation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeCompensation> findByCriteria(EmployeeCompensationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeCompensation> specification = createSpecification(criteria);
        return employeeCompensationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeCompensation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeCompensation> findByCriteria(EmployeeCompensationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeCompensation> specification = createSpecification(criteria);
        return employeeCompensationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCompensationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeCompensation> specification = createSpecification(criteria);
        return employeeCompensationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCompensationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeCompensation> createSpecification(EmployeeCompensationCriteria criteria) {
        Specification<EmployeeCompensation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeCompensation_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EmployeeCompensation_.date));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmployeeCompensation_.type));
            }
            if (criteria.getCommitmentuntil() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCommitmentuntil(), EmployeeCompensation_.commitmentuntil));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), EmployeeCompensation_.comments));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeCompensation_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeCompensation_.updatedat));
            }
            if (criteria.getReasoncomments() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReasoncomments(), EmployeeCompensation_.reasoncomments));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeCompensation_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getReasonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReasonId(),
                            root -> root.join(EmployeeCompensation_.reason, JoinType.LEFT).get(Reasons_.id)
                        )
                    );
            }
            if (criteria.getCompensationbenefitsEmployeecompensationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompensationbenefitsEmployeecompensationId(),
                            root ->
                                root
                                    .join(EmployeeCompensation_.compensationbenefitsEmployeecompensations, JoinType.LEFT)
                                    .get(CompensationBenefits_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
