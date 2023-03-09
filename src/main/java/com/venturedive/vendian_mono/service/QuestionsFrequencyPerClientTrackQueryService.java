package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerClientTrackRepository;
import com.venturedive.vendian_mono.service.criteria.QuestionsFrequencyPerClientTrackCriteria;
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
 * Service for executing complex queries for {@link QuestionsFrequencyPerClientTrack} entities in the database.
 * The main input is a {@link QuestionsFrequencyPerClientTrackCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionsFrequencyPerClientTrack} or a {@link Page} of {@link QuestionsFrequencyPerClientTrack} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsFrequencyPerClientTrackQueryService extends QueryService<QuestionsFrequencyPerClientTrack> {

    private final Logger log = LoggerFactory.getLogger(QuestionsFrequencyPerClientTrackQueryService.class);

    private final QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository;

    public QuestionsFrequencyPerClientTrackQueryService(
        QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository
    ) {
        this.questionsFrequencyPerClientTrackRepository = questionsFrequencyPerClientTrackRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionsFrequencyPerClientTrack} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionsFrequencyPerClientTrack> findByCriteria(QuestionsFrequencyPerClientTrackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuestionsFrequencyPerClientTrack> specification = createSpecification(criteria);
        return questionsFrequencyPerClientTrackRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QuestionsFrequencyPerClientTrack} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsFrequencyPerClientTrack> findByCriteria(QuestionsFrequencyPerClientTrackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuestionsFrequencyPerClientTrack> specification = createSpecification(criteria);
        return questionsFrequencyPerClientTrackRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsFrequencyPerClientTrackCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuestionsFrequencyPerClientTrack> specification = createSpecification(criteria);
        return questionsFrequencyPerClientTrackRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsFrequencyPerClientTrackCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuestionsFrequencyPerClientTrack> createSpecification(QuestionsFrequencyPerClientTrackCriteria criteria) {
        Specification<QuestionsFrequencyPerClientTrack> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuestionsFrequencyPerClientTrack_.id));
            }
            if (criteria.getQuestion() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getQuestion(), QuestionsFrequencyPerClientTrack_.question));
            }
            if (criteria.getFrequency() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFrequency(), QuestionsFrequencyPerClientTrack_.frequency));
            }
            if (criteria.getCreatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreatedat(), QuestionsFrequencyPerClientTrack_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getUpdatedat(), QuestionsFrequencyPerClientTrack_.updatedat));
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(QuestionsFrequencyPerClientTrack_.project, JoinType.LEFT).get(Projects_.id)
                        )
                    );
            }
            if (criteria.getTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTrackId(),
                            root -> root.join(QuestionsFrequencyPerClientTrack_.track, JoinType.LEFT).get(Tracks_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
