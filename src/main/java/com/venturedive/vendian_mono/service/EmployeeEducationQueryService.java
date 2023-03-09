package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.repository.EmployeeEducationRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeEducationCriteria;
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
 * Service for executing complex queries for {@link EmployeeEducation} entities in the database.
 * The main input is a {@link EmployeeEducationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeEducation} or a {@link Page} of {@link EmployeeEducation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeEducationQueryService extends QueryService<EmployeeEducation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeEducationQueryService.class);

    private final EmployeeEducationRepository employeeEducationRepository;

    public EmployeeEducationQueryService(EmployeeEducationRepository employeeEducationRepository) {
        this.employeeEducationRepository = employeeEducationRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeEducation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeEducation> findByCriteria(EmployeeEducationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeEducation> specification = createSpecification(criteria);
        return employeeEducationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeEducation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeEducation> findByCriteria(EmployeeEducationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeEducation> specification = createSpecification(criteria);
        return employeeEducationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeEducationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeEducation> specification = createSpecification(criteria);
        return employeeEducationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeEducationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeEducation> createSpecification(EmployeeEducationCriteria criteria) {
        Specification<EmployeeEducation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeEducation_.id));
            }
            if (criteria.getInstitute() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstitute(), EmployeeEducation_.institute));
            }
            if (criteria.getMajor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMajor(), EmployeeEducation_.major));
            }
            if (criteria.getDegree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDegree(), EmployeeEducation_.degree));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), EmployeeEducation_.value));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), EmployeeEducation_.city));
            }
            if (criteria.getProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvince(), EmployeeEducation_.province));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), EmployeeEducation_.country));
            }
            if (criteria.getDatefrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatefrom(), EmployeeEducation_.datefrom));
            }
            if (criteria.getDateto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateto(), EmployeeEducation_.dateto));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeEducation_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeEducation_.updatedat));
            }
            if (criteria.getQualificationtypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQualificationtypeId(),
                            root -> root.join(EmployeeEducation_.qualificationtype, JoinType.LEFT).get(QualificationTypes_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeEducation_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
