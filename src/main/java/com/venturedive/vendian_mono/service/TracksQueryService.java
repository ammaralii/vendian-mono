package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.TracksRepository;
import com.venturedive.vendian_mono.service.criteria.TracksCriteria;
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
 * Service for executing complex queries for {@link Tracks} entities in the database.
 * The main input is a {@link TracksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tracks} or a {@link Page} of {@link Tracks} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TracksQueryService extends QueryService<Tracks> {

    private final Logger log = LoggerFactory.getLogger(TracksQueryService.class);

    private final TracksRepository tracksRepository;

    public TracksQueryService(TracksRepository tracksRepository) {
        this.tracksRepository = tracksRepository;
    }

    /**
     * Return a {@link List} of {@link Tracks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tracks> findByCriteria(TracksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tracks> specification = createSpecification(criteria);
        return tracksRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tracks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tracks> findByCriteria(TracksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tracks> specification = createSpecification(criteria);
        return tracksRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TracksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tracks> specification = createSpecification(criteria);
        return tracksRepository.count(specification);
    }

    /**
     * Function to convert {@link TracksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tracks> createSpecification(TracksCriteria criteria) {
        Specification<Tracks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tracks_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Tracks_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Tracks_.description));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Tracks_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Tracks_.updatedat));
            }
            if (criteria.getDeletedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedat(), Tracks_.deletedat));
            }
            if (criteria.getCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompetencyId(),
                            root -> root.join(Tracks_.competency, JoinType.LEFT).get(Competencies_.id)
                        )
                    );
            }
            if (criteria.getInterviewsTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInterviewsTrackId(),
                            root -> root.join(Tracks_.interviewsTracks, JoinType.LEFT).get(Interviews_.id)
                        )
                    );
            }
            if (criteria.getQuestionsTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsTrackId(),
                            root -> root.join(Tracks_.questionsTracks, JoinType.LEFT).get(Questions_.id)
                        )
                    );
            }
            if (criteria.getQuestionsfrequencyperclienttrackTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsfrequencyperclienttrackTrackId(),
                            root ->
                                root
                                    .join(Tracks_.questionsfrequencyperclienttrackTracks, JoinType.LEFT)
                                    .get(QuestionsFrequencyPerClientTrack_.id)
                        )
                    );
            }
            if (criteria.getQuestionsfrequencypertrackTrackId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuestionsfrequencypertrackTrackId(),
                            root -> root.join(Tracks_.questionsfrequencypertrackTracks, JoinType.LEFT).get(QuestionsFrequencyPerTrack_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
