package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs;
import com.venturedive.vendian_mono.repository.QuestionsProcessingEventLogsRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsProcessingEventLogsCriteria;
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
 * Service for executing complex queries for {@link QuestionsProcessingEventLogs} entities in the database.
 * The main input is a {@link QuestionsProcessingEventLogsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionsProcessingEventLogs} or a {@link Page} of {@link QuestionsProcessingEventLogs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsProcessingEventLogsQueryService extends QueryService<QuestionsProcessingEventLogs> {

    private final Logger log = LoggerFactory.getLogger(QuestionsProcessingEventLogsQueryService.class);

    private final QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository;

    public QuestionsProcessingEventLogsQueryService(QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository) {
        this.questionsProcessingEventLogsRepository = questionsProcessingEventLogsRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionsProcessingEventLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionsProcessingEventLogs> findByCriteria(QuestionsProcessingEventLogsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionsProcessingEventLogs> specification = createSpecification(criteria);
        return questionsProcessingEventLogsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionsProcessingEventLogs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsProcessingEventLogs> findByCriteria(QuestionsProcessingEventLogsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionsProcessingEventLogs> specification = createSpecification(criteria);
        return questionsProcessingEventLogsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsProcessingEventLogsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionsProcessingEventLogs> specification = createSpecification(criteria);
        return questionsProcessingEventLogsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsProcessingEventLogsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionsProcessingEventLogs> createSpecification(QuestionsProcessingEventLogsCriteria criteria) {
        Specification<QuestionsProcessingEventLogs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionsProcessingEventLogs_.id));
            }
            if (criteria.getProcessedOn() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getProcessedOn(), QuestionsProcessingEventLogs_.processedOn));
            }
            if (criteria.getCreatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedat(), QuestionsProcessingEventLogs_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedat(), QuestionsProcessingEventLogs_.updatedat));
            }
        }
        return specification;
    }
}
