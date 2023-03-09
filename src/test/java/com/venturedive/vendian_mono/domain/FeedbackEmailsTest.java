package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeedbackEmailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedbackEmails.class);
        FeedbackEmails feedbackEmails1 = new FeedbackEmails();
        feedbackEmails1.setId(1L);
        FeedbackEmails feedbackEmails2 = new FeedbackEmails();
        feedbackEmails2.setId(feedbackEmails1.getId());
        assertThat(feedbackEmails1).isEqualTo(feedbackEmails2);
        feedbackEmails2.setId(2L);
        assertThat(feedbackEmails1).isNotEqualTo(feedbackEmails2);
        feedbackEmails1.setId(null);
        assertThat(feedbackEmails1).isNotEqualTo(feedbackEmails2);
    }
}
