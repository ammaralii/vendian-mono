package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedbackRespondents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRespondentsRepository
    extends JpaRepository<FeedbackRespondents, Long>, JpaSpecificationExecutor<FeedbackRespondents> {}
