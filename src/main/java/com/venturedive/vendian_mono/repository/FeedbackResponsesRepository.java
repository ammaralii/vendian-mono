package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.FeedbackResponses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedbackResponses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackResponsesRepository extends JpaRepository<FeedbackResponses, Long>, JpaSpecificationExecutor<FeedbackResponses> {}
