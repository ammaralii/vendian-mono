package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.repository.FeedbackRespondentsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackRespondentsCriteria;
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
 * Service for executing complex queries for {@link FeedbackRespondents} entities in the database.
 * The main input is a {@link FeedbackRespondentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedbackRespondents} or a {@link Page} of {@link FeedbackRespondents} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedbackRespondentsQueryService extends QueryService<FeedbackRespondents> {

    private final Logger log = LoggerFactory.getLogger(FeedbackRespondentsQueryService.class);

    private final FeedbackRespondentsRepository feedbackRespondentsRepository;

    public FeedbackRespondentsQueryService(FeedbackRespondentsRepository feedbackRespondentsRepository) {
        this.feedbackRespondentsRepository = feedbackRespondentsRepository;
    }

    /**
     * Return a {@link List} of {@link FeedbackRespondents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackRespondents> findByCriteria(FeedbackRespondentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedbackRespondents> specification = createSpecification(criteria);
        return feedbackRespondentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeedbackRespondents} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackRespondents> findByCriteria(FeedbackRespondentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedbackRespondents> specification = createSpecification(criteria);
        return feedbackRespondentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedbackRespondentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedbackRespondents> specification = createSpecification(criteria);
        return feedbackRespondentsRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedbackRespondentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedbackRespondents> createSpecification(FeedbackRespondentsCriteria criteria) {
        Specification<FeedbackRespondents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedbackRespondents_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategory(), FeedbackRespondents_.category));
            }
            if (criteria.getHasresponded() != null) {
                specification = specification.and(buildSpecification(criteria.getHasresponded(), FeedbackRespondents_.hasresponded));
            }
            if (criteria.getRespondedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRespondedat(), FeedbackRespondents_.respondedat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), FeedbackRespondents_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), FeedbackRespondents_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(FeedbackRespondents_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRequestId(),
                            root -> root.join(FeedbackRespondents_.request, JoinType.LEFT).get(FeedbackRequests_.id)
                        )
                    );
            }
            if (criteria.getFeedbackemailsRespondentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackemailsRespondentId(),
                            root -> root.join(FeedbackRespondents_.feedbackemailsRespondents, JoinType.LEFT).get(FeedbackEmails_.id)
                        )
                    );
            }
            if (criteria.getFeedbackresponsesRespondentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackresponsesRespondentId(),
                            root -> root.join(FeedbackRespondents_.feedbackresponsesRespondents, JoinType.LEFT).get(FeedbackResponses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
