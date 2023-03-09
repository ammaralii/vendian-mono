package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.UserRatingsRemarksRepository;
import com.venturedive.vendian_mono.service.criteria.UserRatingsRemarksCriteria;
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
 * Service for executing complex queries for {@link UserRatingsRemarks} entities in the database.
 * The main input is a {@link UserRatingsRemarksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRatingsRemarks} or a {@link Page} of {@link UserRatingsRemarks} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRatingsRemarksQueryService extends QueryService<UserRatingsRemarks> {

    private final Logger log = LoggerFactory.getLogger(UserRatingsRemarksQueryService.class);

    private final UserRatingsRemarksRepository userRatingsRemarksRepository;

    public UserRatingsRemarksQueryService(UserRatingsRemarksRepository userRatingsRemarksRepository) {
        this.userRatingsRemarksRepository = userRatingsRemarksRepository;
    }

    /**
     * Return a {@link List} of {@link UserRatingsRemarks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRatingsRemarks> findByCriteria(UserRatingsRemarksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRatingsRemarks> specification = createSpecification(criteria);
        return userRatingsRemarksRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserRatingsRemarks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRatingsRemarks> findByCriteria(UserRatingsRemarksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRatingsRemarks> specification = createSpecification(criteria);
        return userRatingsRemarksRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRatingsRemarksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRatingsRemarks> specification = createSpecification(criteria);
        return userRatingsRemarksRepository.count(specification);
    }

    /**
     * Function to convert {@link UserRatingsRemarksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserRatingsRemarks> createSpecification(UserRatingsRemarksCriteria criteria) {
        Specification<UserRatingsRemarks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserRatingsRemarks_.id));
            }
            if (criteria.getIsPromotion() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPromotion(), UserRatingsRemarks_.isPromotion));
            }
            if (criteria.getStrength() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStrength(), UserRatingsRemarks_.strength));
            }
            if (criteria.getAreaOfImprovement() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAreaOfImprovement(), UserRatingsRemarks_.areaOfImprovement));
            }
            if (criteria.getPromotionJustification() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getPromotionJustification(), UserRatingsRemarks_.promotionJustification)
                    );
            }
            if (criteria.getNewGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewGrade(), UserRatingsRemarks_.newGrade));
            }
            if (criteria.getIsRedesignation() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRedesignation(), UserRatingsRemarks_.isRedesignation));
            }
            if (criteria.getRecommendedSalary() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRecommendedSalary(), UserRatingsRemarks_.recommendedSalary));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), UserRatingsRemarks_.status));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserRatingsRemarks_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserRatingsRemarks_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), UserRatingsRemarks_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserRatingsRemarks_.version));
            }
            if (criteria.getOtherComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherComments(), UserRatingsRemarks_.otherComments));
            }
            if (criteria.getDesignationAfterPromotionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationAfterPromotionId(),
                            root -> root.join(UserRatingsRemarks_.designationAfterPromotion, JoinType.LEFT).get(Designations_.id)
                        )
                    );
            }
            if (criteria.getDesignationAfterRedesignationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDesignationAfterRedesignationId(),
                            root -> root.join(UserRatingsRemarks_.designationAfterRedesignation, JoinType.LEFT).get(Designations_.id)
                        )
                    );
            }
            if (criteria.getRaterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRaterId(),
                            root -> root.join(UserRatingsRemarks_.rater, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getPcEmployeeRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPcEmployeeRatingId(),
                            root -> root.join(UserRatingsRemarks_.pcEmployeeRating, JoinType.LEFT).get(PerformanceCycleEmployeeRating_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
