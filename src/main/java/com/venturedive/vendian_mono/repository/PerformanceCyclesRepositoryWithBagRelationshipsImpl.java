package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PerformanceCycles;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PerformanceCyclesRepositoryWithBagRelationshipsImpl implements PerformanceCyclesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PerformanceCycles> fetchBagRelationships(Optional<PerformanceCycles> performanceCycles) {
        return performanceCycles.map(this::fetchProjectcycles).map(this::fetchEmployeeratings);
    }

    @Override
    public Page<PerformanceCycles> fetchBagRelationships(Page<PerformanceCycles> performanceCycles) {
        return new PageImpl<>(
            fetchBagRelationships(performanceCycles.getContent()),
            performanceCycles.getPageable(),
            performanceCycles.getTotalElements()
        );
    }

    @Override
    public List<PerformanceCycles> fetchBagRelationships(List<PerformanceCycles> performanceCycles) {
        return Optional.of(performanceCycles).map(this::fetchProjectcycles).map(this::fetchEmployeeratings).orElse(Collections.emptyList());
    }

    PerformanceCycles fetchProjectcycles(PerformanceCycles result) {
        return entityManager
            .createQuery(
                "select performanceCycles from PerformanceCycles performanceCycles left join fetch performanceCycles.projectcycles where performanceCycles is :performanceCycles",
                PerformanceCycles.class
            )
            .setParameter("performanceCycles", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PerformanceCycles> fetchProjectcycles(List<PerformanceCycles> performanceCycles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, performanceCycles.size()).forEach(index -> order.put(performanceCycles.get(index).getId(), index));
        List<PerformanceCycles> result = entityManager
            .createQuery(
                "select distinct performanceCycles from PerformanceCycles performanceCycles left join fetch performanceCycles.projectcycles where performanceCycles in :performanceCycles",
                PerformanceCycles.class
            )
            .setParameter("performanceCycles", performanceCycles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    PerformanceCycles fetchEmployeeratings(PerformanceCycles result) {
        return entityManager
            .createQuery(
                "select performanceCycles from PerformanceCycles performanceCycles left join fetch performanceCycles.employeeratings where performanceCycles is :performanceCycles",
                PerformanceCycles.class
            )
            .setParameter("performanceCycles", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PerformanceCycles> fetchEmployeeratings(List<PerformanceCycles> performanceCycles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, performanceCycles.size()).forEach(index -> order.put(performanceCycles.get(index).getId(), index));
        List<PerformanceCycles> result = entityManager
            .createQuery(
                "select distinct performanceCycles from PerformanceCycles performanceCycles left join fetch performanceCycles.employeeratings where performanceCycles in :performanceCycles",
                PerformanceCycles.class
            )
            .setParameter("performanceCycles", performanceCycles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
