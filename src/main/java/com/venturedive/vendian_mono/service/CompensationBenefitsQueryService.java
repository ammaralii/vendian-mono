package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.repository.CompensationBenefitsRepository;
import com.venturedive.vendian_mono.service.criteria.CompensationBenefitsCriteria;
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
 * Service for executing complex queries for {@link CompensationBenefits} entities in the database.
 * The main input is a {@link CompensationBenefitsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompensationBenefits} or a {@link Page} of {@link CompensationBenefits} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompensationBenefitsQueryService extends QueryService<CompensationBenefits> {

    private final Logger log = LoggerFactory.getLogger(CompensationBenefitsQueryService.class);

    private final CompensationBenefitsRepository compensationBenefitsRepository;

    public CompensationBenefitsQueryService(CompensationBenefitsRepository compensationBenefitsRepository) {
        this.compensationBenefitsRepository = compensationBenefitsRepository;
    }

    /**
     * Return a {@link List} of {@link CompensationBenefits} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompensationBenefits> findByCriteria(CompensationBenefitsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompensationBenefits> specification = createSpecification(criteria);
        return compensationBenefitsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CompensationBenefits} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompensationBenefits> findByCriteria(CompensationBenefitsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompensationBenefits> specification = createSpecification(criteria);
        return compensationBenefitsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompensationBenefitsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompensationBenefits> specification = createSpecification(criteria);
        return compensationBenefitsRepository.count(specification);
    }

    /**
     * Function to convert {@link CompensationBenefitsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompensationBenefits> createSpecification(CompensationBenefitsCriteria criteria) {
        Specification<CompensationBenefits> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompensationBenefits_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CompensationBenefits_.amount));
            }
            if (criteria.getIsdeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdeleted(), CompensationBenefits_.isdeleted));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), CompensationBenefits_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), CompensationBenefits_.updatedat));
            }
            if (criteria.getBenefitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBenefitId(),
                            root -> root.join(CompensationBenefits_.benefit, JoinType.LEFT).get(Benefits_.id)
                        )
                    );
            }
            if (criteria.getEmployeecompensationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecompensationId(),
                            root -> root.join(CompensationBenefits_.employeecompensation, JoinType.LEFT).get(EmployeeCompensation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
