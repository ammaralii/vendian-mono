package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.repository.InterviewsRepository;
import com.venturedive.vendian_mono.service.criteria.InterviewsCriteria;
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
 * Service for executing complex queries for {@link Interviews} entities in the database.
 * The main input is a {@link InterviewsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Interviews} or a {@link Page} of {@link Interviews} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterviewsQueryService extends QueryService<Interviews> {

    private final Logger log = LoggerFactory.getLogger(InterviewsQueryService.class);

    private final InterviewsRepository interviewsRepository;

    public InterviewsQueryService(InterviewsRepository interviewsRepository) {
        this.interviewsRepository = interviewsRepository;
    }

    /**
     * Return a {@link List} of {@link Interviews} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Interviews> findByCriteria(InterviewsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Interviews> specification = createSpecification(criteria);
        return interviewsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Interviews} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviews> findByCriteria(InterviewsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Interviews> specification = createSpecification(criteria);
        return interviewsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterviewsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Interviews> specification = createSpecification(criteria);
        return interviewsRepository.count(specification);
    }

    /**
     * Function to convert {@link InterviewsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Interviews> createSpecification(InterviewsCriteria criteria) {
        Specification<Interviews> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Interviews_.id));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResult(), Interviews_.result));
            }
            if (criteria.getClientcomments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientcomments(), Interviews_.clientcomments));
            }
            if (criteria.getLmcomments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLmcomments(), Interviews_.lmcomments));
            }
            if (criteria.getScheduledat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScheduledat(), Interviews_.scheduledat));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Interviews_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Interviews_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Interviews_.deletedat));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(Interviews_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProjectId(), root -> root.join(Interviews_.project, JoinType.LEFT).get(Projects_.id))
                    );
            }
            if (criteria.getTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTrackId(), root -> root.join(Interviews_.track, JoinType.LEFT).get(Tracks_.id))
                    );
            }
            if (criteria.getQuestionsInterviewId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsInterviewId(),
                            root -> root.join(Interviews_.questionsInterviews, JoinType.LEFT).get(Questions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
