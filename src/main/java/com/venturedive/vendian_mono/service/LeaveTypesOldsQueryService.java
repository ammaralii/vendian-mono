package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypesOlds;
import com.venturedive.vendian_mono.repository.LeaveTypesOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypesOldsCriteria;
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
 * Service for executing complex queries for {@link LeaveTypesOlds} entities in the database.
 * The main input is a {@link LeaveTypesOldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypesOlds} or a {@link Page} of {@link LeaveTypesOlds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypesOldsQueryService extends QueryService<LeaveTypesOlds> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesOldsQueryService.class);

    private final LeaveTypesOldsRepository leaveTypesOldsRepository;

    public LeaveTypesOldsQueryService(LeaveTypesOldsRepository leaveTypesOldsRepository) {
        this.leaveTypesOldsRepository = leaveTypesOldsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypesOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypesOlds> findByCriteria(LeaveTypesOldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypesOlds> specification = createSpecification(criteria);
        return leaveTypesOldsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypesOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypesOlds> findByCriteria(LeaveTypesOldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypesOlds> specification = createSpecification(criteria);
        return leaveTypesOldsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypesOldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypesOlds> specification = createSpecification(criteria);
        return leaveTypesOldsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypesOldsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypesOlds> createSpecification(LeaveTypesOldsCriteria criteria) {
        Specification<LeaveTypesOlds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypesOlds_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveTypesOlds_.name));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), LeaveTypesOlds_.isactive));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), LeaveTypesOlds_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), LeaveTypesOlds_.updatedat));
            }
            if (criteria.getLeaverequestsoldsLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaverequestsoldsLeavetypeId(),
                            root -> root.join(LeaveTypesOlds_.leaverequestsoldsLeavetypes, JoinType.LEFT).get(LeaveRequestsOlds_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
