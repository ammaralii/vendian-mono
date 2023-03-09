package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackRespondentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackRespondents.class);
        FeedbackRespondents feedbackRespondents1 = new FeedbackRespondents();
        feedbackRespondents1.setId(1L);
        FeedbackRespondents feedbackRespondents2 = new FeedbackRespondents();
        feedbackRespondents2.setId(feedbackRespondents1.getId());
        assertThat(feedbackRespondents1).isEqualTo(feedbackRespondents2);
        feedbackRespondents2.setId(2L);
        assertThat(feedbackRespondents1).isNotEqualTo(feedbackRespondents2);
        feedbackRespondents1.setId(null);
        assertThat(feedbackRespondents1).isNotEqualTo(feedbackRespondents2);
    }
}
