package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Designations;
import com.venturedive.vendian_mono.repository.DesignationsRepository;
import com.venturedive.vendian_mono.service.criteria.DesignationsCriteria;
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
 * Service for executing complex queries for {@link Designations} entities in the database.
 * The main input is a {@link DesignationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Designations} or a {@link Page} of {@link Designations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesignationsQueryService extends QueryService<Designations> {

    private final Logger log = LoggerFactory.getLogger(DesignationsQueryService.class);

    private final DesignationsRepository designationsRepository;

    public DesignationsQueryService(DesignationsRepository designationsRepository) {
        this.designationsRepository = designationsRepository;
    }

    /**
     * Return a {@link List} of {@link Designations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Designations> findByCriteria(DesignationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Designations> specification = createSpecification(criteria);
        return designationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Designations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Designations> findByCriteria(DesignationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Designations> specification = createSpecification(criteria);
        return designationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesignationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Designations> specification = createSpecification(criteria);
        return designationsRepository.count(specification);
    }

    /**
     * Function to convert {@link DesignationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Designations> createSpecification(DesignationsCriteria criteria) {
        Specification<Designations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Designations_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Designations_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Designations_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Designations_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Designations_.deletedat));
            }
            if (criteria.getDesignationjobdescriptionsDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationjobdescriptionsDesignationId(),
                            root ->
                                root
                                    .join(Designations_.designationjobdescriptionsDesignations, JoinType.LEFT)
                                    .get(DesignationJobDescriptions_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoDesignationId(),
                            root -> root.join(Designations_.employeejobinfoDesignations, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeesDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesDesignationId(),
                            root -> root.join(Designations_.employeesDesignations, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getUserratingsremarksDesignationafterpromotionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsremarksDesignationafterpromotionId(),
                            root ->
                                root
                                    .join(Designations_.userratingsremarksDesignationafterpromotions, JoinType.LEFT)
                                    .get(UserRatingsRemarks_.id)
                        )
                    );
            }
            if (criteria.getUserratingsremarksDesignationafterredesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserratingsremarksDesignationafterredesignationId(),
                            root ->
                                root
                                    .join(Designations_.userratingsremarksDesignationafterredesignations, JoinType.LEFT)
                                    .get(UserRatingsRemarks_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
