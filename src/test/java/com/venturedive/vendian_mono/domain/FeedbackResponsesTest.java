package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackResponsesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackResponses.class);
        FeedbackResponses feedbackResponses1 = new FeedbackResponses();
        feedbackResponses1.setId(1L);
        FeedbackResponses feedbackResponses2 = new FeedbackResponses();
        feedbackResponses2.setId(feedbackResponses1.getId());
        assertThat(feedbackResponses1).isEqualTo(feedbackResponses2);
        feedbackResponses2.setId(2L);
        assertThat(feedbackResponses1).isNotEqualTo(feedbackResponses2);
        feedbackResponses1.setId(null);
        assertThat(feedbackResponses1).isNotEqualTo(feedbackResponses2);
    }
}
