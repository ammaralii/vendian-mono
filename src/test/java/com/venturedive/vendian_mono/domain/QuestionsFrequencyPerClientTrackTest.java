package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionsFrequencyPerClientTrackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsFrequencyPerClientTrack.class);
        QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack1 = new QuestionsFrequencyPerClientTrack();
        questionsFrequencyPerClientTrack1.setId(1L);
        QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack2 = new QuestionsFrequencyPerClientTrack();
        questionsFrequencyPerClientTrack2.setId(questionsFrequencyPerClientTrack1.getId());
        assertThat(questionsFrequencyPerClientTrack1).isEqualTo(questionsFrequencyPerClientTrack2);
        questionsFrequencyPerClientTrack2.setId(2L);
        assertThat(questionsFrequencyPerClientTrack1).isNotEqualTo(questionsFrequencyPerClientTrack2);
        questionsFrequencyPerClientTrack1.setId(null);
        assertThat(questionsFrequencyPerClientTrack1).isNotEqualTo(questionsFrequencyPerClientTrack2);
    }
}
