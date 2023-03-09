package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.ProjectCycles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProjectCyclesRepositoryWithBagRelationships {
    Optional<ProjectCycles> fetchBagRelationships(Optional<ProjectCycles> projectCycles);

    List<ProjectCycles> fetchBagRelationships(List<ProjectCycles> projectCycles);

    Page<ProjectCycles> fetchBagRelationships(Page<ProjectCycles> projectCycles);
}
