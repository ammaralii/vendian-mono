package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.NotificationMergeFields;
import com.venturedive.vendian_mono.repository.NotificationMergeFieldsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationMergeFieldsCriteria;
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
 * Service for executing complex queries for {@link NotificationMergeFields} entities in the database.
 * The main input is a {@link NotificationMergeFieldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationMergeFields} or a {@link Page} of {@link NotificationMergeFields} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationMergeFieldsQueryService extends QueryService<NotificationMergeFields> {

    private final Logger log = LoggerFactory.getLogger(NotificationMergeFieldsQueryService.class);

    private final NotificationMergeFieldsRepository notificationMergeFieldsRepository;

    public NotificationMergeFieldsQueryService(NotificationMergeFieldsRepository notificationMergeFieldsRepository) {
        this.notificationMergeFieldsRepository = notificationMergeFieldsRepository;
    }

    /**
     * Return a {@link List} of {@link NotificationMergeFields} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationMergeFields> findByCriteria(NotificationMergeFieldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationMergeFields> specification = createSpecification(criteria);
        return notificationMergeFieldsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotificationMergeFields} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationMergeFields> findByCriteria(NotificationMergeFieldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationMergeFields> specification = createSpecification(criteria);
        return notificationMergeFieldsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationMergeFieldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationMergeFields> specification = createSpecification(criteria);
        return notificationMergeFieldsRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationMergeFieldsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationMergeFields> createSpecification(NotificationMergeFieldsCriteria criteria) {
        Specification<NotificationMergeFields> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationMergeFields_.id));
            }
            if (criteria.getField() != null) {
                specification = specification.and(buildStringSpecification(criteria.getField(), NotificationMergeFields_.field));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), NotificationMergeFields_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), NotificationMergeFields_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), NotificationMergeFields_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), NotificationMergeFields_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), NotificationMergeFields_.version));
            }
            if (criteria.getNotificationEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationEventId(),
                            root -> root.join(NotificationMergeFields_.notificationEvent, JoinType.LEFT).get(NotificationEvents_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
