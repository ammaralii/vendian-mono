package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QuestionsFrequencyPerTrack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsFrequencyPerTrackRepository
    extends JpaRepository<QuestionsFrequencyPerTrack, Long>, JpaSpecificationExecutor<QuestionsFrequencyPerTrack> {}
