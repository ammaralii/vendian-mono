package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeAddresses;
import com.venturedive.vendian_mono.repository.EmployeeAddressesRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeAddressesCriteria;
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
 * Service for executing complex queries for {@link EmployeeAddresses} entities in the database.
 * The main input is a {@link EmployeeAddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeAddresses} or a {@link Page} of {@link EmployeeAddresses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeAddressesQueryService extends QueryService<EmployeeAddresses> {

    private final Logger log = LoggerFactory.getLogger(EmployeeAddressesQueryService.class);

    private final EmployeeAddressesRepository employeeAddressesRepository;

    public EmployeeAddressesQueryService(EmployeeAddressesRepository employeeAddressesRepository) {
        this.employeeAddressesRepository = employeeAddressesRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeAddresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeAddresses> findByCriteria(EmployeeAddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeAddresses> specification = createSpecification(criteria);
        return employeeAddressesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeAddresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAddresses> findByCriteria(EmployeeAddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeAddresses> specification = createSpecification(criteria);
        return employeeAddressesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeAddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeAddresses> specification = createSpecification(criteria);
        return employeeAddressesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeAddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeAddresses> createSpecification(EmployeeAddressesCriteria criteria) {
        Specification<EmployeeAddresses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeAddresses_.id));
            }
            if (criteria.getIsdeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdeleted(), EmployeeAddresses_.isdeleted));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeAddresses_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeAddresses_.updatedat));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmployeeAddresses_.type));
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(EmployeeAddresses_.address, JoinType.LEFT).get(Addresses_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeAddresses_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
