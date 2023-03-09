package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.repository.PcRatingsTrainingsRepository;
import com.venturedive.vendian_mono.service.criteria.PcRatingsTrainingsCriteria;
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
 * Service for executing complex queries for {@link PcRatingsTrainings} entities in the database.
 * The main input is a {@link PcRatingsTrainingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PcRatingsTrainings} or a {@link Page} of {@link PcRatingsTrainings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PcRatingsTrainingsQueryService extends QueryService<PcRatingsTrainings> {

    private final Logger log = LoggerFactory.getLogger(PcRatingsTrainingsQueryService.class);

    private final PcRatingsTrainingsRepository pcRatingsTrainingsRepository;

    public PcRatingsTrainingsQueryService(PcRatingsTrainingsRepository pcRatingsTrainingsRepository) {
        this.pcRatingsTrainingsRepository = pcRatingsTrainingsRepository;
    }

    /**
     * Return a {@link List} of {@link PcRatingsTrainings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PcRatingsTrainings> findByCriteria(PcRatingsTrainingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PcRatingsTrainings> specification = createSpecification(criteria);
        return pcRatingsTrainingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PcRatingsTrainings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingsTrainings> findByCriteria(PcRatingsTrainingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PcRatingsTrainings> specification = createSpecification(criteria);
        return pcRatingsTrainingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PcRatingsTrainingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PcRatingsTrainings> specification = createSpecification(criteria);
        return pcRatingsTrainingsRepository.count(specification);
    }

    /**
     * Function to convert {@link PcRatingsTrainingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PcRatingsTrainings> createSpecification(PcRatingsTrainingsCriteria criteria) {
        Specification<PcRatingsTrainings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PcRatingsTrainings_.id));
            }
            if (criteria.getStrength() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStrength(), PcRatingsTrainings_.strength));
            }
            if (criteria.getDevelopmentArea() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDevelopmentArea(), PcRatingsTrainings_.developmentArea));
            }
            if (criteria.getCareerAmbition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCareerAmbition(), PcRatingsTrainings_.careerAmbition));
            }
            if (criteria.getShortCourse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortCourse(), PcRatingsTrainings_.shortCourse));
            }
            if (criteria.getTechnicalTraining() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTechnicalTraining(), PcRatingsTrainings_.technicalTraining));
            }
            if (criteria.getSoftSkillsTraining() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSoftSkillsTraining(), PcRatingsTrainings_.softSkillsTraining));
            }
            if (criteria.getCriticalPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getCriticalPosition(), PcRatingsTrainings_.criticalPosition));
            }
            if (criteria.getHighPotential() != null) {
                specification = specification.and(buildSpecification(criteria.getHighPotential(), PcRatingsTrainings_.highPotential));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PcRatingsTrainings_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PcRatingsTrainings_.updatedAt));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), PcRatingsTrainings_.deletedAt));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), PcRatingsTrainings_.version));
            }
            if (criteria.getSuccessorForId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuccessorForId(),
                            root -> root.join(PcRatingsTrainings_.successorFor, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getRatingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRatingId(),
                            root -> root.join(PcRatingsTrainings_.rating, JoinType.LEFT).get(PcRatings_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
