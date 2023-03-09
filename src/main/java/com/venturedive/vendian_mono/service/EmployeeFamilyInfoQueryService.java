package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import com.venturedive.vendian_mono.repository.EmployeeFamilyInfoRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeFamilyInfoCriteria;
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
 * Service for executing complex queries for {@link EmployeeFamilyInfo} entities in the database.
 * The main input is a {@link EmployeeFamilyInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeFamilyInfo} or a {@link Page} of {@link EmployeeFamilyInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeFamilyInfoQueryService extends QueryService<EmployeeFamilyInfo> {

    private final Logger log = LoggerFactory.getLogger(EmployeeFamilyInfoQueryService.class);

    private final EmployeeFamilyInfoRepository employeeFamilyInfoRepository;

    public EmployeeFamilyInfoQueryService(EmployeeFamilyInfoRepository employeeFamilyInfoRepository) {
        this.employeeFamilyInfoRepository = employeeFamilyInfoRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeFamilyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeFamilyInfo> findByCriteria(EmployeeFamilyInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeFamilyInfo> specification = createSpecification(criteria);
        return employeeFamilyInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeFamilyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeFamilyInfo> findByCriteria(EmployeeFamilyInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeFamilyInfo> specification = createSpecification(criteria);
        return employeeFamilyInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeFamilyInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeFamilyInfo> specification = createSpecification(criteria);
        return employeeFamilyInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeFamilyInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeFamilyInfo> createSpecification(EmployeeFamilyInfoCriteria criteria) {
        Specification<EmployeeFamilyInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeFamilyInfo_.id));
            }
            if (criteria.getFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullname(), EmployeeFamilyInfo_.fullname));
            }
            if (criteria.getRelationship() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelationship(), EmployeeFamilyInfo_.relationship));
            }
            if (criteria.getContactno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactno(), EmployeeFamilyInfo_.contactno));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), EmployeeFamilyInfo_.email));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), EmployeeFamilyInfo_.dob));
            }
            if (criteria.getRegisteredinmedical() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getRegisteredinmedical(), EmployeeFamilyInfo_.registeredinmedical));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeFamilyInfo_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeFamilyInfo_.updatedat));
            }
            if (criteria.getMedicalpolicyno() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMedicalpolicyno(), EmployeeFamilyInfo_.medicalpolicyno));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), EmployeeFamilyInfo_.gender));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeFamilyInfo_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
