package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeavesCopy;
import com.venturedive.vendian_mono.repository.LeavesCopyRepository;
import com.venturedive.vendian_mono.service.criteria.LeavesCopyCriteria;
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
 * Service for executing complex queries for {@link LeavesCopy} entities in the database.
 * The main input is a {@link LeavesCopyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeavesCopy} or a {@link Page} of {@link LeavesCopy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeavesCopyQueryService extends QueryService<LeavesCopy> {

    private final Logger log = LoggerFactory.getLogger(LeavesCopyQueryService.class);

    private final LeavesCopyRepository leavesCopyRepository;

    public LeavesCopyQueryService(LeavesCopyRepository leavesCopyRepository) {
        this.leavesCopyRepository = leavesCopyRepository;
    }

    /**
     * Return a {@link List} of {@link LeavesCopy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeavesCopy> findByCriteria(LeavesCopyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeavesCopy> specification = createSpecification(criteria);
        return leavesCopyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeavesCopy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavesCopy> findByCriteria(LeavesCopyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeavesCopy> specification = createSpecification(criteria);
        return leavesCopyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeavesCopyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeavesCopy> specification = createSpecification(criteria);
        return leavesCopyRepository.count(specification);
    }

    /**
     * Function to convert {@link LeavesCopyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeavesCopy> createSpecification(LeavesCopyCriteria criteria) {
        Specification<LeavesCopy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeavesCopy_.id));
            }
            if (criteria.getAnnualtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualtotal(), LeavesCopy_.annualtotal));
            }
            if (criteria.getAnnualused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualused(), LeavesCopy_.annualused));
            }
            if (criteria.getAnnualadjustments() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualadjustments(), LeavesCopy_.annualadjustments));
            }
            if (criteria.getCasualtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCasualtotal(), LeavesCopy_.casualtotal));
            }
            if (criteria.getCasualused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCasualused(), LeavesCopy_.casualused));
            }
            if (criteria.getSicktotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSicktotal(), LeavesCopy_.sicktotal));
            }
            if (criteria.getSickused() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSickused(), LeavesCopy_.sickused));
            }
            if (criteria.getAnnualtotalnextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAnnualtotalnextyear(), LeavesCopy_.annualtotalnextyear));
            }
            if (criteria.getAnnualusednextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAnnualusednextyear(), LeavesCopy_.annualusednextyear));
            }
            if (criteria.getCasualtotalnextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCasualtotalnextyear(), LeavesCopy_.casualtotalnextyear));
            }
            if (criteria.getCasualusednextyear() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCasualusednextyear(), LeavesCopy_.casualusednextyear));
            }
            if (criteria.getSicktotalnextyear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSicktotalnextyear(), LeavesCopy_.sicktotalnextyear));
            }
            if (criteria.getSickusednextyear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSickusednextyear(), LeavesCopy_.sickusednextyear));
            }
            if (criteria.getCarryforward() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarryforward(), LeavesCopy_.carryforward));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), LeavesCopy_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), LeavesCopy_.updatedat));
            }
        }
        return specification;
    }
}
