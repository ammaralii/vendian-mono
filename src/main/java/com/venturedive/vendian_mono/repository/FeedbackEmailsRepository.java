package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.FeedbackEmails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedbackEmails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackEmailsRepository extends JpaRepository<FeedbackEmails, Long>, JpaSpecificationExecutor<FeedbackEmails> {}
