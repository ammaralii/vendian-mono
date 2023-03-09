package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Addresses;
import com.venturedive.vendian_mono.repository.AddressesRepository;
import com.venturedive.vendian_mono.service.criteria.AddressesCriteria;
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
 * Service for executing complex queries for {@link Addresses} entities in the database.
 * The main input is a {@link AddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Addresses} or a {@link Page} of {@link Addresses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressesQueryService extends QueryService<Addresses> {

    private final Logger log = LoggerFactory.getLogger(AddressesQueryService.class);

    private final AddressesRepository addressesRepository;

    public AddressesQueryService(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    /**
     * Return a {@link List} of {@link Addresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Addresses> findByCriteria(AddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Addresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Addresses> findByCriteria(AddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Addresses> createSpecification(AddressesCriteria criteria) {
        Specification<Addresses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Addresses_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Addresses_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Addresses_.city));
            }
            if (criteria.getProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvince(), Addresses_.province));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Addresses_.country));
            }
            if (criteria.getPostalcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalcode(), Addresses_.postalcode));
            }
            if (criteria.getIsdeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdeleted(), Addresses_.isdeleted));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Addresses_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Addresses_.updatedat));
            }
            if (criteria.getEmployeeaddressesAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeaddressesAddressId(),
                            root -> root.join(Addresses_.employeeaddressesAddresses, JoinType.LEFT).get(EmployeeAddresses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
