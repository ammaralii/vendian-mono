package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.NotificationSentEmailLogs;
import com.venturedive.vendian_mono.repository.NotificationSentEmailLogsRepository;
import com.venturedive.vendian_mono.service.criteria.NotificationSentEmailLogsCriteria;
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
 * Service for executing complex queries for {@link NotificationSentEmailLogs} entities in the database.
 * The main input is a {@link NotificationSentEmailLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationSentEmailLogs} or a {@link Page} of {@link NotificationSentEmailLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationSentEmailLogsQueryService extends QueryService<NotificationSentEmailLogs> {

    private final Logger log = LoggerFactory.getLogger(NotificationSentEmailLogsQueryService.class);

    private final NotificationSentEmailLogsRepository notificationSentEmailLogsRepository;

    public NotificationSentEmailLogsQueryService(NotificationSentEmailLogsRepository notificationSentEmailLogsRepository) {
        this.notificationSentEmailLogsRepository = notificationSentEmailLogsRepository;
    }

    /**
     * Return a {@link List} of {@link NotificationSentEmailLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationSentEmailLogs> findByCriteria(NotificationSentEmailLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationSentEmailLogs> specification = createSpecification(criteria);
        return notificationSentEmailLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotificationSentEmailLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationSentEmailLogs> findByCriteria(NotificationSentEmailLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationSentEmailLogs> specification = createSpecification(criteria);
        return notificationSentEmailLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationSentEmailLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationSentEmailLogs> specification = createSpecification(criteria);
        return notificationSentEmailLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationSentEmailLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationSentEmailLogs> createSpecification(NotificationSentEmailLogsCriteria criteria) {
        Specification<NotificationSentEmailLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationSentEmailLogs_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), NotificationSentEmailLogs_.email));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), NotificationSentEmailLogs_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), NotificationSentEmailLogs_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), NotificationSentEmailLogs_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), NotificationSentEmailLogs_.version));
            }
            if (criteria.getNotificationTemplateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationTemplateId(),
                            root -> root.join(NotificationSentEmailLogs_.notificationTemplate, JoinType.LEFT).get(NotificationTemplates_.id)
                        )
                    );
            }
            if (criteria.getRecipientId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRecipientId(),
                            root -> root.join(NotificationSentEmailLogs_.recipient, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
