package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.NotificationEvents;
import com.venturedive.vendian_mono.repository.NotificationEventsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationEventsCriteria;
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
 * Service for executing complex queries for {@link NotificationEvents} entities in the database.
 * The main input is a {@link NotificationEventsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationEvents} or a {@link Page} of {@link NotificationEvents} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationEventsQueryService extends QueryService<NotificationEvents> {

    private final Logger log = LoggerFactory.getLogger(NotificationEventsQueryService.class);

    private final NotificationEventsRepository notificationEventsRepository;

    public NotificationEventsQueryService(NotificationEventsRepository notificationEventsRepository) {
        this.notificationEventsRepository = notificationEventsRepository;
    }

    /**
     * Return a {@link List} of {@link NotificationEvents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationEvents> findByCriteria(NotificationEventsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationEvents> specification = createSpecification(criteria);
        return notificationEventsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotificationEvents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationEvents> findByCriteria(NotificationEventsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationEvents> specification = createSpecification(criteria);
        return notificationEventsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationEventsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationEvents> specification = createSpecification(criteria);
        return notificationEventsRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationEventsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationEvents> createSpecification(NotificationEventsCriteria criteria) {
        Specification<NotificationEvents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationEvents_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NotificationEvents_.name));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), NotificationEvents_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), NotificationEvents_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), NotificationEvents_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), NotificationEvents_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), NotificationEvents_.version));
            }
            if (criteria.getNotificationmergefieldsNotificationeventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationmergefieldsNotificationeventId(),
                            root ->
                                root
                                    .join(NotificationEvents_.notificationmergefieldsNotificationevents, JoinType.LEFT)
                                    .get(NotificationMergeFields_.id)
                        )
                    );
            }
            if (criteria.getNotificationtemplatesNotificationeventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationtemplatesNotificationeventId(),
                            root ->
                                root
                                    .join(NotificationEvents_.notificationtemplatesNotificationevents, JoinType.LEFT)
                                    .get(NotificationTemplates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
