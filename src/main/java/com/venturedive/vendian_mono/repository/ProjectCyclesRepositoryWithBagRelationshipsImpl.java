package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectCycles;
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
public class ProjectCyclesRepositoryWithBagRelationshipsImpl implements ProjectCyclesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ProjectCycles> fetchBagRelationships(Optional<ProjectCycles> projectCycles) {
        return projectCycles.map(this::fetchRatings);
    }

    @Override
    public Page<ProjectCycles> fetchBagRelationships(Page<ProjectCycles> projectCycles) {
        return new PageImpl<>(
            fetchBagRelationships(projectCycles.getContent()),
            projectCycles.getPageable(),
            projectCycles.getTotalElements()
        );
    }

    @Override
    public List<ProjectCycles> fetchBagRelationships(List<ProjectCycles> projectCycles) {
        return Optional.of(projectCycles).map(this::fetchRatings).orElse(Collections.emptyList());
    }

    ProjectCycles fetchRatings(ProjectCycles result) {
        return entityManager
            .createQuery(
                "select projectCycles from ProjectCycles projectCycles left join fetch projectCycles.ratings where projectCycles is :projectCycles",
                ProjectCycles.class
            )
            .setParameter("projectCycles", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ProjectCycles> fetchRatings(List<ProjectCycles> projectCycles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, projectCycles.size()).forEach(index -> order.put(projectCycles.get(index).getId(), index));
        List<ProjectCycles> result = entityManager
            .createQuery(
                "select distinct projectCycles from ProjectCycles projectCycles left join fetch projectCycles.ratings where projectCycles in :projectCycles",
                ProjectCycles.class
            )
            .setParameter("projectCycles", projectCycles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
