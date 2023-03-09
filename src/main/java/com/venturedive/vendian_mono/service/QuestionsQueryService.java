package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.repository.QuestionsRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsCriteria;
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
 * Service for executing complex queries for {@link Questions} entities in the database.
 * The main input is a {@link QuestionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Questions} or a {@link Page} of {@link Questions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsQueryService extends QueryService<Questions> {

    private final Logger log = LoggerFactory.getLogger(QuestionsQueryService.class);

    private final QuestionsRepository questionsRepository;

    public QuestionsQueryService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    /**
     * Return a {@link List} of {@link Questions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Questions> findByCriteria(QuestionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Questions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Questions> findByCriteria(QuestionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Questions> createSpecification(QuestionsCriteria criteria) {
        Specification<Questions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Questions_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Questions_.text));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), Questions_.answer));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Questions_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Questions_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Questions_.deletedat));
            }
            if (criteria.getCleaneduptext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCleaneduptext(), Questions_.cleaneduptext));
            }
            if (criteria.getInterviewId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewId(),
                            root -> root.join(Questions_.interview, JoinType.LEFT).get(Interviews_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProjectId(), root -> root.join(Questions_.project, JoinType.LEFT).get(Projects_.id))
                    );
            }
            if (criteria.getTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTrackId(), root -> root.join(Questions_.track, JoinType.LEFT).get(Tracks_.id))
                    );
            }
        }
        return specification;
    }
}
