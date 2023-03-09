package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import com.venturedive.vendian_mono.repository.LeaveTypeConfigurationsRepository;
import com.venturedive.vendian_mono.service.criteria.LeaveTypeConfigurationsCriteria;
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
 * Service for executing complex queries for {@link LeaveTypeConfigurations} entities in the database.
 * The main input is a {@link LeaveTypeConfigurationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypeConfigurations} or a {@link Page} of {@link LeaveTypeConfigurations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeConfigurationsQueryService extends QueryService<LeaveTypeConfigurations> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeConfigurationsQueryService.class);

    private final LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository;

    public LeaveTypeConfigurationsQueryService(LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository) {
        this.leaveTypeConfigurationsRepository = leaveTypeConfigurationsRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypeConfigurations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypeConfigurations> findByCriteria(LeaveTypeConfigurationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveTypeConfigurations> specification = createSpecification(criteria);
        return leaveTypeConfigurationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveTypeConfigurations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeConfigurations> findByCriteria(LeaveTypeConfigurationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveTypeConfigurations> specification = createSpecification(criteria);
        return leaveTypeConfigurationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeConfigurationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveTypeConfigurations> specification = createSpecification(criteria);
        return leaveTypeConfigurationsRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveTypeConfigurationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveTypeConfigurations> createSpecification(LeaveTypeConfigurationsCriteria criteria) {
        Specification<LeaveTypeConfigurations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveTypeConfigurations_.id));
            }
            if (criteria.getNoOfLeaves() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfLeaves(), LeaveTypeConfigurations_.noOfLeaves));
            }
            if (criteria.getTenureCycle() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTenureCycle(), LeaveTypeConfigurations_.tenureCycle));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), LeaveTypeConfigurations_.to));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), LeaveTypeConfigurations_.from));
            }
            if (criteria.getInclusivity() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInclusivity(), LeaveTypeConfigurations_.inclusivity));
            }
            if (criteria.getMaxQuota() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxQuota(), LeaveTypeConfigurations_.maxQuota));
            }
            if (criteria.getIsAccured() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAccured(), LeaveTypeConfigurations_.isAccured));
            }
            if (criteria.getEffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffDate(), LeaveTypeConfigurations_.effDate));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeaveTypeConfigurations_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeaveTypeConfigurations_.updatedAt));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), LeaveTypeConfigurations_.endDate));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), LeaveTypeConfigurations_.version));
            }
            if (criteria.getLeaveTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLeaveTypeId(),
                            root -> root.join(LeaveTypeConfigurations_.leaveType, JoinType.LEFT).get(LeaveTypes_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
