package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackResponsesRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackResponsesCriteria;
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
 * Service for executing complex queries for {@link FeedbackResponses} entities in the database.
 * The main input is a {@link FeedbackResponsesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedbackResponses} or a {@link Page} of {@link FeedbackResponses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedbackResponsesQueryService extends QueryService<FeedbackResponses> {

    private final Logger log = LoggerFactory.getLogger(FeedbackResponsesQueryService.class);

    private final FeedbackResponsesRepository feedbackResponsesRepository;

    public FeedbackResponsesQueryService(FeedbackResponsesRepository feedbackResponsesRepository) {
        this.feedbackResponsesRepository = feedbackResponsesRepository;
    }

    /**
     * Return a {@link List} of {@link FeedbackResponses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackResponses> findByCriteria(FeedbackResponsesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedbackResponses> specification = createSpecification(criteria);
        return feedbackResponsesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeedbackResponses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackResponses> findByCriteria(FeedbackResponsesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedbackResponses> specification = createSpecification(criteria);
        return feedbackResponsesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedbackResponsesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedbackResponses> specification = createSpecification(criteria);
        return feedbackResponsesRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedbackResponsesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedbackResponses> createSpecification(FeedbackResponsesCriteria criteria) {
        Specification<FeedbackResponses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedbackResponses_.id));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), FeedbackResponses_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), FeedbackResponses_.updatedat));
            }
            if (criteria.getRespondentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRespondentId(),
                            root -> root.join(FeedbackResponses_.respondent, JoinType.LEFT).get(FeedbackRespondents_.id)
                        )
                    );
            }
            if (criteria.getQuestionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionId(),
                            root -> root.join(FeedbackResponses_.question, JoinType.LEFT).get(FeedbackQuestions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
