package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackRequestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackRequests.class);
        FeedbackRequests feedbackRequests1 = new FeedbackRequests();
        feedbackRequests1.setId(1L);
        FeedbackRequests feedbackRequests2 = new FeedbackRequests();
        feedbackRequests2.setId(feedbackRequests1.getId());
        assertThat(feedbackRequests1).isEqualTo(feedbackRequests2);
        feedbackRequests2.setId(2L);
        assertThat(feedbackRequests1).isNotEqualTo(feedbackRequests2);
        feedbackRequests1.setId(null);
        assertThat(feedbackRequests1).isNotEqualTo(feedbackRequests2);
    }
}
