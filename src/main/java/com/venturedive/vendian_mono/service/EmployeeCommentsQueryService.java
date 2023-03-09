package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.repository.EmployeeCommentsRepository;
import com.venturedive.vendian_mono.service.criteria.EmployeeCommentsCriteria;
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
 * Service for executing complex queries for {@link EmployeeComments} entities in the database.
 * The main input is a {@link EmployeeCommentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeComments} or a {@link Page} of {@link EmployeeComments} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeCommentsQueryService extends QueryService<EmployeeComments> {

    private final Logger log = LoggerFactory.getLogger(EmployeeCommentsQueryService.class);

    private final EmployeeCommentsRepository employeeCommentsRepository;

    public EmployeeCommentsQueryService(EmployeeCommentsRepository employeeCommentsRepository) {
        this.employeeCommentsRepository = employeeCommentsRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeComments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeComments> findByCriteria(EmployeeCommentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeComments> specification = createSpecification(criteria);
        return employeeCommentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeComments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeComments> findByCriteria(EmployeeCommentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeComments> specification = createSpecification(criteria);
        return employeeCommentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCommentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeComments> specification = createSpecification(criteria);
        return employeeCommentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCommentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeComments> createSpecification(EmployeeCommentsCriteria criteria) {
        Specification<EmployeeComments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeComments_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), EmployeeComments_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), EmployeeComments_.updatedat));
            }
            if (criteria.getDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentId(),
                            root -> root.join(EmployeeComments_.document, JoinType.LEFT).get(Documents_.id)
                        )
                    );
            }
            if (criteria.getCommenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommenterId(),
                            root -> root.join(EmployeeComments_.commenter, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeComments_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
