package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeDocuments;
import com.venturedive.vendian_mono.repository.EmployeeDocumentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeDocumentsCriteria;
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
 * Service for executing complex queries for {@link EmployeeDocuments} entities in the database.
 * The main input is a {@link EmployeeDocumentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDocuments} or a {@link Page} of {@link EmployeeDocuments} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDocumentsQueryService extends QueryService<EmployeeDocuments> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDocumentsQueryService.class);

    private final EmployeeDocumentsRepository employeeDocumentsRepository;

    public EmployeeDocumentsQueryService(EmployeeDocumentsRepository employeeDocumentsRepository) {
        this.employeeDocumentsRepository = employeeDocumentsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeDocuments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDocuments> findByCriteria(EmployeeDocumentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDocuments> specification = createSpecification(criteria);
        return employeeDocumentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeDocuments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDocuments> findByCriteria(EmployeeDocumentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDocuments> specification = createSpecification(criteria);
        return employeeDocumentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDocumentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDocuments> specification = createSpecification(criteria);
        return employeeDocumentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDocumentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDocuments> createSpecification(EmployeeDocumentsCriteria criteria) {
        Specification<EmployeeDocuments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDocuments_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeDocuments_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeDocuments_.updatedat));
            }
            if (criteria.getDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentId(),
                            root -> root.join(EmployeeDocuments_.document, JoinType.LEFT).get(Documents_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeDocuments_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
