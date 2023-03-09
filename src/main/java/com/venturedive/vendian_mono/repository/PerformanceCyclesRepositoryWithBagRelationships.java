package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.PerformanceCycles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PerformanceCyclesRepositoryWithBagRelationships {
    Optional<PerformanceCycles> fetchBagRelationships(Optional<PerformanceCycles> performanceCycles);

    List<PerformanceCycles> fetchBagRelationships(List<PerformanceCycles> performanceCycles);

    Page<PerformanceCycles> fetchBagRelationships(Page<PerformanceCycles> performanceCycles);
}
