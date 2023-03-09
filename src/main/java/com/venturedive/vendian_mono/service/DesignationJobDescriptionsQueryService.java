package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.DesignationJobDescriptions;
import com.venturedive.vendian_mono.repository.DesignationJobDescriptionsRepository;
import com.venturedive.vendian_mono.service.criteria.DesignationJobDescriptionsCriteria;
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
 * Service for executing complex queries for {@link DesignationJobDescriptions} entities in the database.
 * The main input is a {@link DesignationJobDescriptionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DesignationJobDescriptions} or a {@link Page} of {@link DesignationJobDescriptions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesignationJobDescriptionsQueryService extends QueryService<DesignationJobDescriptions> {

    private final Logger log = LoggerFactory.getLogger(DesignationJobDescriptionsQueryService.class);

    private final DesignationJobDescriptionsRepository designationJobDescriptionsRepository;

    public DesignationJobDescriptionsQueryService(DesignationJobDescriptionsRepository designationJobDescriptionsRepository) {
        this.designationJobDescriptionsRepository = designationJobDescriptionsRepository;
    }

    /**
     * Return a {@link List} of {@link DesignationJobDescriptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DesignationJobDescriptions> findByCriteria(DesignationJobDescriptionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DesignationJobDescriptions> specification = createSpecification(criteria);
        return designationJobDescriptionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DesignationJobDescriptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DesignationJobDescriptions> findByCriteria(DesignationJobDescriptionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DesignationJobDescriptions> specification = createSpecification(criteria);
        return designationJobDescriptionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesignationJobDescriptionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DesignationJobDescriptions> specification = createSpecification(criteria);
        return designationJobDescriptionsRepository.count(specification);
    }

    /**
     * Function to convert {@link DesignationJobDescriptionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DesignationJobDescriptions> createSpecification(DesignationJobDescriptionsCriteria criteria) {
        Specification<DesignationJobDescriptions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DesignationJobDescriptions_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), DesignationJobDescriptions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), DesignationJobDescriptions_.updatedat));
            }
            if (criteria.getDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentId(),
                            root -> root.join(DesignationJobDescriptions_.document, JoinType.LEFT).get(Documents_.id)
                        )
                    );
            }
            if (criteria.getDesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationId(),
                            root -> root.join(DesignationJobDescriptions_.designation, JoinType.LEFT).get(Designations_.id)
                        )
                    );
            }
            if (criteria.getEmployeejobinfoJobdescriptionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoJobdescriptionId(),
                            root ->
                                root
                                    .join(DesignationJobDescriptions_.employeejobinfoJobdescriptions, JoinType.LEFT)
                                    .get(EmployeeJobInfo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
