package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.repository.DocumentsRepository;
import com.venturedive.vendian_mono.service.criteria.DocumentsCriteria;
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
 * Service for executing complex queries for {@link Documents} entities in the database.
 * The main input is a {@link DocumentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Documents} or a {@link Page} of {@link Documents} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentsQueryService extends QueryService<Documents> {

    private final Logger log = LoggerFactory.getLogger(DocumentsQueryService.class);

    private final DocumentsRepository documentsRepository;

    public DocumentsQueryService(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    /**
     * Return a {@link List} of {@link Documents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Documents> findByCriteria(DocumentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Documents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Documents> findByCriteria(DocumentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Documents> createSpecification(DocumentsCriteria criteria) {
        Specification<Documents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Documents_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Documents_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Documents_.updatedat));
            }
            if (criteria.getDesignationjobdescriptionsDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationjobdescriptionsDocumentId(),
                            root ->
                                root.join(Documents_.designationjobdescriptionsDocuments, JoinType.LEFT).get(DesignationJobDescriptions_.id)
                        )
                    );
            }
            if (criteria.getEmployeecommentsDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecommentsDocumentId(),
                            root -> root.join(Documents_.employeecommentsDocuments, JoinType.LEFT).get(EmployeeComments_.id)
                        )
                    );
            }
            if (criteria.getEmployeedocumentsDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeedocumentsDocumentId(),
                            root -> root.join(Documents_.employeedocumentsDocuments, JoinType.LEFT).get(EmployeeDocuments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
