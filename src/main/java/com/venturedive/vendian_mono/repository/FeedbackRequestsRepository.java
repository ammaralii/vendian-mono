package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.FeedbackRequests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedbackRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRequestsRepository extends JpaRepository<FeedbackRequests, Long>, JpaSpecificationExecutor<FeedbackRequests> {}
