package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.DealResourceEventLogs;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealResourceEventLogs entity.
 */
@Repository
public interface DealResourceEventLogsRepository
    extends JpaRepository<DealResourceEventLogs, Long>, JpaSpecificationExecutor<DealResourceEventLogs> {
    default Optional<DealResourceEventLogs> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DealResourceEventLogs> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DealResourceEventLogs> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dealResourceEventLogs from DealResourceEventLogs dealResourceEventLogs left join fetch dealResourceEventLogs.resourcestatus left join fetch dealResourceEventLogs.systemstatus",
        countQuery = "select count(distinct dealResourceEventLogs) from DealResourceEventLogs dealResourceEventLogs"
    )
    Page<DealResourceEventLogs> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dealResourceEventLogs from DealResourceEventLogs dealResourceEventLogs left join fetch dealResourceEventLogs.resourcestatus left join fetch dealResourceEventLogs.systemstatus"
    )
    List<DealResourceEventLogs> findAllWithToOneRelationships();

    @Query(
        "select dealResourceEventLogs from DealResourceEventLogs dealResourceEventLogs left join fetch dealResourceEventLogs.resourcestatus left join fetch dealResourceEventLogs.systemstatus where dealResourceEventLogs.id =:id"
    )
    Optional<DealResourceEventLogs> findOneWithToOneRelationships(@Param("id") Long id);
}
