package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.repository.FeedbackRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.FeedbackRequestsCriteria;
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
 * Service for executing complex queries for {@link FeedbackRequests} entities in the database.
 * The main input is a {@link FeedbackRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeedbackRequests} or a {@link Page} of {@link FeedbackRequests} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedbackRequestsQueryService extends QueryService<FeedbackRequests> {

    private final Logger log = LoggerFactory.getLogger(FeedbackRequestsQueryService.class);

    private final FeedbackRequestsRepository feedbackRequestsRepository;

    public FeedbackRequestsQueryService(FeedbackRequestsRepository feedbackRequestsRepository) {
        this.feedbackRequestsRepository = feedbackRequestsRepository;
    }

    /**
     * Return a {@link List} of {@link FeedbackRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackRequests> findByCriteria(FeedbackRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FeedbackRequests> specification = createSpecification(criteria);
        return feedbackRequestsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FeedbackRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackRequests> findByCriteria(FeedbackRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FeedbackRequests> specification = createSpecification(criteria);
        return feedbackRequestsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedbackRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FeedbackRequests> specification = createSpecification(criteria);
        return feedbackRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedbackRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FeedbackRequests> createSpecification(FeedbackRequestsCriteria criteria) {
        Specification<FeedbackRequests> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FeedbackRequests_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), FeedbackRequests_.status));
            }
            if (criteria.getIsreportavailable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsreportavailable(), FeedbackRequests_.isreportavailable));
            }
            if (criteria.getReportpath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReportpath(), FeedbackRequests_.reportpath));
            }
            if (criteria.getApprovedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovedat(), FeedbackRequests_.approvedat));
            }
            if (criteria.getExpiredat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiredat(), FeedbackRequests_.expiredat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), FeedbackRequests_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), FeedbackRequests_.updatedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(FeedbackRequests_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getLinemanagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLinemanagerId(),
                            root -> root.join(FeedbackRequests_.linemanager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getFeedbackrespondentsRequestId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeedbackrespondentsRequestId(),
                            root -> root.join(FeedbackRequests_.feedbackrespondentsRequests, JoinType.LEFT).get(FeedbackRespondents_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
