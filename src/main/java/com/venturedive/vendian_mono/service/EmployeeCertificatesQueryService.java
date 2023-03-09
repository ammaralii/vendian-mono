package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import com.venturedive.vendian_mono.repository.EmployeeCertificatesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeCertificatesCriteria;
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
 * Service for executing complex queries for {@link EmployeeCertificates} entities in the database.
 * The main input is a {@link EmployeeCertificatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeCertificates} or a {@link Page} of {@link EmployeeCertificates} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeCertificatesQueryService extends QueryService<EmployeeCertificates> {

    private final Logger log = LoggerFactory.getLogger(EmployeeCertificatesQueryService.class);

    private final EmployeeCertificatesRepository employeeCertificatesRepository;

    public EmployeeCertificatesQueryService(EmployeeCertificatesRepository employeeCertificatesRepository) {
        this.employeeCertificatesRepository = employeeCertificatesRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeCertificates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeCertificates> findByCriteria(EmployeeCertificatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeCertificates> specification = createSpecification(criteria);
        return employeeCertificatesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeCertificates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeCertificates> findByCriteria(EmployeeCertificatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeCertificates> specification = createSpecification(criteria);
        return employeeCertificatesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCertificatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeCertificates> specification = createSpecification(criteria);
        return employeeCertificatesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCertificatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeCertificates> createSpecification(EmployeeCertificatesCriteria criteria) {
        Specification<EmployeeCertificates> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeCertificates_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmployeeCertificates_.name));
            }
            if (criteria.getCertificateno() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCertificateno(), EmployeeCertificates_.certificateno));
            }
            if (criteria.getIssuingbody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIssuingbody(), EmployeeCertificates_.issuingbody));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EmployeeCertificates_.date));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeCertificates_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeCertificates_.updatedat));
            }
            if (criteria.getValidtill() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidtill(), EmployeeCertificates_.validtill));
            }
            if (criteria.getCertificatecompetency() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCertificatecompetency(), EmployeeCertificates_.certificatecompetency)
                    );
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), EmployeeCertificates_.deletedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeCertificates_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
