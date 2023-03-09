package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.repository.UserAttributesRepository;
import com.venturedive.vendian_mono.service.criteria.UserAttributesCriteria;
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
 * Service for executing complex queries for {@link UserAttributes} entities in the database.
 * The main input is a {@link UserAttributesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAttributes} or a {@link Page} of {@link UserAttributes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAttributesQueryService extends QueryService<UserAttributes> {

    private final Logger log = LoggerFactory.getLogger(UserAttributesQueryService.class);

    private final UserAttributesRepository userAttributesRepository;

    public UserAttributesQueryService(UserAttributesRepository userAttributesRepository) {
        this.userAttributesRepository = userAttributesRepository;
    }

    /**
     * Return a {@link List} of {@link UserAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAttributes> findByCriteria(UserAttributesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAttributes> specification = createSpecification(criteria);
        return userAttributesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserAttributes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributes> findByCriteria(UserAttributesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAttributes> specification = createSpecification(criteria);
        return userAttributesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAttributesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAttributes> specification = createSpecification(criteria);
        return userAttributesRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAttributesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAttributes> createSpecification(UserAttributesCriteria criteria) {
        Specification<UserAttributes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAttributes_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserAttributes_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserAttributes_.updatedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserAttributes_.version));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserAttributes_.endDate));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), UserAttributes_.effDate));
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(UserAttributes_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserAttributes_.user, JoinType.LEFT).get(Employees_.id))
                    );
            }
        }
        return specification;
    }
}
