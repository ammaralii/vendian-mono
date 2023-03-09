package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.repository.CompetenciesRepository;
import com.venturedive.vendian_mono.service.criteria.CompetenciesCriteria;
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
 * Service for executing complex queries for {@link Competencies} entities in the database.
 * The main input is a {@link CompetenciesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Competencies} or a {@link Page} of {@link Competencies} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompetenciesQueryService extends QueryService<Competencies> {

    private final Logger log = LoggerFactory.getLogger(CompetenciesQueryService.class);

    private final CompetenciesRepository competenciesRepository;

    public CompetenciesQueryService(CompetenciesRepository competenciesRepository) {
        this.competenciesRepository = competenciesRepository;
    }

    /**
     * Return a {@link List} of {@link Competencies} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Competencies> findByCriteria(CompetenciesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Competencies> specification = createSpecification(criteria);
        return competenciesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Competencies} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Competencies> findByCriteria(CompetenciesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Competencies> specification = createSpecification(criteria);
        return competenciesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompetenciesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Competencies> specification = createSpecification(criteria);
        return competenciesRepository.count(specification);
    }

    /**
     * Function to convert {@link CompetenciesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Competencies> createSpecification(CompetenciesCriteria criteria) {
        Specification<Competencies> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Competencies_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Competencies_.name));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Competencies_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Competencies_.updatedat));
            }
            if (criteria.getEmployeejobinfoCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeejobinfoCompetencyId(),
                            root -> root.join(Competencies_.employeejobinfoCompetencies, JoinType.LEFT).get(EmployeeJobInfo_.id)
                        )
                    );
            }
            if (criteria.getEmployeesCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesCompetencyId(),
                            root -> root.join(Competencies_.employeesCompetencies, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getTracksCompetencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTracksCompetencyId(),
                            root -> root.join(Competencies_.tracksCompetencies, JoinType.LEFT).get(Tracks_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
