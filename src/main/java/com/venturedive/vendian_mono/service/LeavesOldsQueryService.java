package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeavesOlds;
import com.venturedive.vendian_mono.repository.LeavesOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesOldsCriteria;
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
 * Service for executing complex queries for {@link LeavesOlds} entities in the database.
 * The main input is a {@link LeavesOldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeavesOlds} or a {@link Page} of {@link LeavesOlds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeavesOldsQueryService extends QueryService<LeavesOlds> {

    private final Logger log = LoggerFactory.getLogger(LeavesOldsQueryService.class);

    private final LeavesOldsRepository leavesOldsRepository;

    public LeavesOldsQueryService(LeavesOldsRepository leavesOldsRepository) {
        this.leavesOldsRepository = leavesOldsRepository;
    }

    /**
     * Return a {@link List} of {@link LeavesOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeavesOlds> findByCriteria(LeavesOldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeavesOlds> specification = createSpecification(criteria);
        return leavesOldsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeavesOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavesOlds> findByCriteria(LeavesOldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeavesOlds> specification = createSpecification(criteria);
        return leavesOldsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeavesOldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeavesOlds> specification = createSpecification(criteria);
        return leavesOldsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeavesOldsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeavesOlds> createSpecification(LeavesOldsCriteria criteria) {
        Specification<LeavesOlds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeavesOlds_.id));
            }
            if (criteria.getAnnualtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualtotal(), LeavesOlds_.annualtotal));
            }
            if (criteria.getAnnualused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualused(), LeavesOlds_.annualused));
            }
            if (criteria.getAnnualadjustments() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualadjustments(), LeavesOlds_.annualadjustments));
            }
            if (criteria.getCasualtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCasualtotal(), LeavesOlds_.casualtotal));
            }
            if (criteria.getCasualused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCasualused(), LeavesOlds_.casualused));
            }
            if (criteria.getSicktotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSicktotal(), LeavesOlds_.sicktotal));
            }
            if (criteria.getSickused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSickused(), LeavesOlds_.sickused));
            }
            if (criteria.getAnnualtotalnextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAnnualtotalnextyear(), LeavesOlds_.annualtotalnextyear));
            }
            if (criteria.getAnnualusednextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAnnualusednextyear(), LeavesOlds_.annualusednextyear));
            }
            if (criteria.getCasualtotalnextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCasualtotalnextyear(), LeavesOlds_.casualtotalnextyear));
            }
            if (criteria.getCasualusednextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCasualusednextyear(), LeavesOlds_.casualusednextyear));
            }
            if (criteria.getSicktotalnextyear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSicktotalnextyear(), LeavesOlds_.sicktotalnextyear));
            }
            if (criteria.getSickusednextyear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSickusednextyear(), LeavesOlds_.sickusednextyear));
            }
            if (criteria.getCarryforward() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarryforward(), LeavesOlds_.carryforward));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), LeavesOlds_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), LeavesOlds_.updatedat));
            }
            if (criteria.getEmployeesLeaveId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesLeaveId(),
                            root -> root.join(LeavesOlds_.employeesLeaves, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
