package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackQuestionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackQuestions.class);
        FeedbackQuestions feedbackQuestions1 = new FeedbackQuestions();
        feedbackQuestions1.setId(1L);
        FeedbackQuestions feedbackQuestions2 = new FeedbackQuestions();
        feedbackQuestions2.setId(feedbackQuestions1.getId());
        assertThat(feedbackQuestions1).isEqualTo(feedbackQuestions2);
        feedbackQuestions2.setId(2L);
        assertThat(feedbackQuestions1).isNotEqualTo(feedbackQuestions2);
        feedbackQuestions1.setId(null);
        assertThat(feedbackQuestions1).isNotEqualTo(feedbackQuestions2);
    }
}
