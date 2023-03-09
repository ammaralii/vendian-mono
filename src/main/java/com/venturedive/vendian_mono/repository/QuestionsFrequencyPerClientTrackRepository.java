package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QuestionsFrequencyPerClientTrack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsFrequencyPerClientTrackRepository
    extends JpaRepository<QuestionsFrequencyPerClientTrack, Long>, JpaSpecificationExecutor<QuestionsFrequencyPerClientTrack> {}
