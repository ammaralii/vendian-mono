package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionsFrequencyPerTrackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsFrequencyPerTrack.class);
        QuestionsFrequencyPerTrack questionsFrequencyPerTrack1 = new QuestionsFrequencyPerTrack();
        questionsFrequencyPerTrack1.setId(1L);
        QuestionsFrequencyPerTrack questionsFrequencyPerTrack2 = new QuestionsFrequencyPerTrack();
        questionsFrequencyPerTrack2.setId(questionsFrequencyPerTrack1.getId());
        assertThat(questionsFrequencyPerTrack1).isEqualTo(questionsFrequencyPerTrack2);
        questionsFrequencyPerTrack2.setId(2L);
        assertThat(questionsFrequencyPerTrack1).isNotEqualTo(questionsFrequencyPerTrack2);
        questionsFrequencyPerTrack1.setId(null);
        assertThat(questionsFrequencyPerTrack1).isNotEqualTo(questionsFrequencyPerTrack2);
    }
}
