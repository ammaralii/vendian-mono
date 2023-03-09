package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.NotificationTemplates;
import com.venturedive.vendian_mono.repository.NotificationTemplatesRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationTemplatesCriteria;
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
 * Service for executing complex queries for {@link NotificationTemplates} entities in the database.
 * The main input is a {@link NotificationTemplatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationTemplates} or a {@link Page} of {@link NotificationTemplates} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationTemplatesQueryService extends QueryService<NotificationTemplates> {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplatesQueryService.class);

    private final NotificationTemplatesRepository notificationTemplatesRepository;

    public NotificationTemplatesQueryService(NotificationTemplatesRepository notificationTemplatesRepository) {
        this.notificationTemplatesRepository = notificationTemplatesRepository;
    }

    /**
     * Return a {@link List} of {@link NotificationTemplates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationTemplates> findByCriteria(NotificationTemplatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationTemplates> specification = createSpecification(criteria);
        return notificationTemplatesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotificationTemplates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationTemplates> findByCriteria(NotificationTemplatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationTemplates> specification = createSpecification(criteria);
        return notificationTemplatesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationTemplatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationTemplates> specification = createSpecification(criteria);
        return notificationTemplatesRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationTemplatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationTemplates> createSpecification(NotificationTemplatesCriteria criteria) {
        Specification<NotificationTemplates> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationTemplates_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NotificationTemplates_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), NotificationTemplates_.type));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), NotificationTemplates_.subject));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), NotificationTemplates_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), NotificationTemplates_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), NotificationTemplates_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), NotificationTemplates_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), NotificationTemplates_.version));
            }
            if (criteria.getNotificationEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationEventId(),
                            root -> root.join(NotificationTemplates_.notificationEvent, JoinType.LEFT).get(NotificationEvents_.id)
                        )
                    );
            }
            if (criteria.getNotificationsentemaillogsNotificationtemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationsentemaillogsNotificationtemplateId(),
                            root ->
                                root
                                    .join(NotificationTemplates_.notificationsentemaillogsNotificationtemplates, JoinType.LEFT)
                                    .get(NotificationSentEmailLogs_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
