package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.Reasons;
import com.venturedive.vendian_mono.repository.ReasonsRepository;
import com.venturedive.vendian_mono.service.criteria.ReasonsCriteria;
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
 * Service for executing complex queries for {@link Reasons} entities in the database.
 * The main input is a {@link ReasonsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Reasons} or a {@link Page} of {@link Reasons} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReasonsQueryService extends QueryService<Reasons> {

    private final Logger log = LoggerFactory.getLogger(ReasonsQueryService.class);

    private final ReasonsRepository reasonsRepository;

    public ReasonsQueryService(ReasonsRepository reasonsRepository) {
        this.reasonsRepository = reasonsRepository;
    }

    /**
     * Return a {@link List} of {@link Reasons} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Reasons> findByCriteria(ReasonsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reasons> specification = createSpecification(criteria);
        return reasonsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Reasons} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Reasons> findByCriteria(ReasonsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reasons> specification = createSpecification(criteria);
        return reasonsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReasonsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reasons> specification = createSpecification(criteria);
        return reasonsRepository.count(specification);
    }

    /**
     * Function to convert {@link ReasonsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reasons> createSpecification(ReasonsCriteria criteria) {
        Specification<Reasons> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reasons_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Reasons_.name));
            }
            if (criteria.getIsdefault() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdefault(), Reasons_.isdefault));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), Reasons_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), Reasons_.updatedat));
            }
            if (criteria.getEmployeecompensationReasonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeecompensationReasonId(),
                            root -> root.join(Reasons_.employeecompensationReasons, JoinType.LEFT).get(EmployeeCompensation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
