package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.repository.UserRelationsRepository;
import com.venturedive.vendian_mono.service.criteria.UserRelationsCriteria;
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
 * Service for executing complex queries for {@link UserRelations} entities in the database.
 * The main input is a {@link UserRelationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRelations} or a {@link Page} of {@link UserRelations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRelationsQueryService extends QueryService<UserRelations> {

    private final Logger log = LoggerFactory.getLogger(UserRelationsQueryService.class);

    private final UserRelationsRepository userRelationsRepository;

    public UserRelationsQueryService(UserRelationsRepository userRelationsRepository) {
        this.userRelationsRepository = userRelationsRepository;
    }

    /**
     * Return a {@link List} of {@link UserRelations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRelations> findByCriteria(UserRelationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRelations> specification = createSpecification(criteria);
        return userRelationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserRelations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRelations> findByCriteria(UserRelationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRelations> specification = createSpecification(criteria);
        return userRelationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRelationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRelations> specification = createSpecification(criteria);
        return userRelationsRepository.count(specification);
    }

    /**
     * Function to convert {@link UserRelationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserRelations> createSpecification(UserRelationsCriteria criteria) {
        Specification<UserRelations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserRelations_.id));
            }
            if (criteria.getReferenceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceType(), UserRelations_.referenceType));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), UserRelations_.referenceId));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), UserRelations_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserRelations_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserRelations_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), UserRelations_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), UserRelations_.version));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserRelations_.user, JoinType.LEFT).get(Employees_.id))
                    );
            }
            if (criteria.getAttributeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttributeId(),
                            root -> root.join(UserRelations_.attribute, JoinType.LEFT).get(Attributes_.id)
                        )
                    );
            }
            if (criteria.getRelatedUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRelatedUserId(),
                            root -> root.join(UserRelations_.relatedUser, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
