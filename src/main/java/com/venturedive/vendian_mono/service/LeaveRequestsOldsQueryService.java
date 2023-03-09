package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveRequestsOlds;
import com.venturedive.vendian_mono.repository.LeaveRequestsOldsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveRequestsOldsCriteria;
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
 * Service for executing complex queries for {@link LeaveRequestsOlds} entities in the database.
 * The main input is a {@link LeaveRequestsOldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveRequestsOlds} or a {@link Page} of {@link LeaveRequestsOlds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveRequestsOldsQueryService extends QueryService<LeaveRequestsOlds> {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsOldsQueryService.class);

    private final LeaveRequestsOldsRepository leaveRequestsOldsRepository;

    public LeaveRequestsOldsQueryService(LeaveRequestsOldsRepository leaveRequestsOldsRepository) {
        this.leaveRequestsOldsRepository = leaveRequestsOldsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveRequestsOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveRequestsOlds> findByCriteria(LeaveRequestsOldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveRequestsOlds> specification = createSpecification(criteria);
        return leaveRequestsOldsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveRequestsOlds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestsOlds> findByCriteria(LeaveRequestsOldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveRequestsOlds> specification = createSpecification(criteria);
        return leaveRequestsOldsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveRequestsOldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveRequestsOlds> specification = createSpecification(criteria);
        return leaveRequestsOldsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveRequestsOldsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveRequestsOlds> createSpecification(LeaveRequestsOldsCriteria criteria) {
        Specification<LeaveRequestsOlds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveRequestsOlds_.id));
            }
            if (criteria.getStartdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartdate(), LeaveRequestsOlds_.startdate));
            }
            if (criteria.getEnddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnddate(), LeaveRequestsOlds_.enddate));
            }
            if (criteria.getRequesternote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequesternote(), LeaveRequestsOlds_.requesternote));
            }
            if (criteria.getManagernote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagernote(), LeaveRequestsOlds_.managernote));
            }
            if (criteria.getCurrentstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentstatus(), LeaveRequestsOlds_.currentstatus));
            }
            if (criteria.getLeavescanceled() != null) {
                specification = specification.and(buildSpecification(criteria.getLeavescanceled(), LeaveRequestsOlds_.leavescanceled));
            }
            if (criteria.getRequestdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestdate(), LeaveRequestsOlds_.requestdate));
            }
            if (criteria.getLinkstring() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkstring(), LeaveRequestsOlds_.linkstring));
            }
            if (criteria.getLinkused() != null) {
                specification = specification.and(buildSpecification(criteria.getLinkused(), LeaveRequestsOlds_.linkused));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), LeaveRequestsOlds_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), LeaveRequestsOlds_.updatedat));
            }
            if (criteria.getIshalfday() != null) {
                specification = specification.and(buildSpecification(criteria.getIshalfday(), LeaveRequestsOlds_.ishalfday));
            }
            if (criteria.getActiondate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActiondate(), LeaveRequestsOlds_.actiondate));
            }
            if (criteria.getLmstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLmstatus(), LeaveRequestsOlds_.lmstatus));
            }
            if (criteria.getPmstatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPmstatus(), LeaveRequestsOlds_.pmstatus));
            }
            if (criteria.getIsbench() != null) {
                specification = specification.and(buildSpecification(criteria.getIsbench(), LeaveRequestsOlds_.isbench));
            }
            if (criteria.getIsescalated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsescalated(), LeaveRequestsOlds_.isescalated));
            }
            if (criteria.getIscommented() != null) {
                specification = specification.and(buildSpecification(criteria.getIscommented(), LeaveRequestsOlds_.iscommented));
            }
            if (criteria.getIsreminded() != null) {
                specification = specification.and(buildSpecification(criteria.getIsreminded(), LeaveRequestsOlds_.isreminded));
            }
            if (criteria.getIsnotified() != null) {
                specification = specification.and(buildSpecification(criteria.getIsnotified(), LeaveRequestsOlds_.isnotified));
            }
            if (criteria.getIsremindedhr() != null) {
                specification = specification.and(buildSpecification(criteria.getIsremindedhr(), LeaveRequestsOlds_.isremindedhr));
            }
            if (criteria.getIsdm() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdm(), LeaveRequestsOlds_.isdm));
            }
            if (criteria.getLeavetypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeavetypeId(),
                            root -> root.join(LeaveRequestsOlds_.leavetype, JoinType.LEFT).get(LeaveTypesOlds_.id)
                        )
                    );
            }
            if (criteria.getManagerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getManagerId(),
                            root -> root.join(LeaveRequestsOlds_.manager, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(LeaveRequestsOlds_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
