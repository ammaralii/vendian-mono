package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.*; // for static metamodels
import com.venturedive.vendian_mono.domain.ZLeaveRequests;
import com.venturedive.vendian_mono.repository.ZLeaveRequestsRepository;
import com.venturedive.vendian_mono.service.criteria.ZLeaveRequestsCriteria;
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
 * Service for executing complex queries for {@link ZLeaveRequests} entities in the database.
 * The main input is a {@link ZLeaveRequestsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZLeaveRequests} or a {@link Page} of {@link ZLeaveRequests} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZLeaveRequestsQueryService extends QueryService<ZLeaveRequests> {

    private final Logger log = LoggerFactory.getLogger(ZLeaveRequestsQueryService.class);

    private final ZLeaveRequestsRepository zLeaveRequestsRepository;

    public ZLeaveRequestsQueryService(ZLeaveRequestsRepository zLeaveRequestsRepository) {
        this.zLeaveRequestsRepository = zLeaveRequestsRepository;
    }

    /**
     * Return a {@link List} of {@link ZLeaveRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZLeaveRequests> findByCriteria(ZLeaveRequestsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZLeaveRequests> specification = createSpecification(criteria);
        return zLeaveRequestsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ZLeaveRequests} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZLeaveRequests> findByCriteria(ZLeaveRequestsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZLeaveRequests> specification = createSpecification(criteria);
        return zLeaveRequestsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZLeaveRequestsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ZLeaveRequests> specification = createSpecification(criteria);
        return zLeaveRequestsRepository.count(specification);
    }

    /**
     * Function to convert {@link ZLeaveRequestsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ZLeaveRequests> createSpecification(ZLeaveRequestsCriteria criteria) {
        Specification<ZLeaveRequests> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ZLeaveRequests_.id));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), ZLeaveRequests_.action));
            }
            if (criteria.getUserid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserid(), ZLeaveRequests_.userid));
            }
            if (criteria.getManagerid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerid(), ZLeaveRequests_.managerid));
            }
            if (criteria.getRequestparams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestparams(), ZLeaveRequests_.requestparams));
            }
            if (criteria.getResponseparams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseparams(), ZLeaveRequests_.responseparams));
            }
            if (criteria.getCreatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedat(), ZLeaveRequests_.createdat));
            }
            if (criteria.getUpdatedat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedat(), ZLeaveRequests_.updatedat));
            }
        }
        return specification;
    }
}
