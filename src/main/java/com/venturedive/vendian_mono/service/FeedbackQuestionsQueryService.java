package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.repository.FeedbackQuestionsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackQuestionsCriteria;
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
 * Service for executing complex queries for {@link FeedbackQuestions} entities in the database.
 * The main input is a {@link FeedbackQuestionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedbackQuestions} or a {@link Page} of {@link FeedbackQuestions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedbackQuestionsQueryService extends QueryService<FeedbackQuestions> {

    private final Logger log = LoggerFactory.getLogger(FeedbackQuestionsQueryService.class);

    private final FeedbackQuestionsRepository feedbackQuestionsRepository;

    public FeedbackQuestionsQueryService(FeedbackQuestionsRepository feedbackQuestionsRepository) {
        this.feedbackQuestionsRepository = feedbackQuestionsRepository;
    }

    /**
     * Return a {@link List} of {@link FeedbackQuestions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackQuestions> findByCriteria(FeedbackQuestionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedbackQuestions> specification = createSpecification(criteria);
        return feedbackQuestionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeedbackQuestions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackQuestions> findByCriteria(FeedbackQuestionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedbackQuestions> specification = createSpecification(criteria);
        return feedbackQuestionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedbackQuestionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedbackQuestions> specification = createSpecification(criteria);
        return feedbackQuestionsRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedbackQuestionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedbackQuestions> createSpecification(FeedbackQuestionsCriteria criteria) {
        Specification<FeedbackQuestions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedbackQuestions_.id));
            }
            if (criteria.getQuestion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuestion(), FeedbackQuestions_.question));
            }
            if (criteria.getIsdefault() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdefault(), FeedbackQuestions_.isdefault));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), FeedbackQuestions_.area));
            }
            if (criteria.getCompetency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompetency(), FeedbackQuestions_.competency));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategory(), FeedbackQuestions_.category));
            }
            if (criteria.getIsskill() != null) {
                specification = specification.and(buildSpecification(criteria.getIsskill(), FeedbackQuestions_.isskill));
            }
            if (criteria.getSkilltype() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSkilltype(), FeedbackQuestions_.skilltype));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), FeedbackQuestions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), FeedbackQuestions_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(FeedbackQuestions_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getFeedbackresponsesQuestionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackresponsesQuestionId(),
                            root -> root.join(FeedbackQuestions_.feedbackresponsesQuestions, JoinType.LEFT).get(FeedbackResponses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
