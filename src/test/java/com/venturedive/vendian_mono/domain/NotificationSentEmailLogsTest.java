package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationSentEmailLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationSentEmailLogs.class);
        NotificationSentEmailLogs notificationSentEmailLogs1 = new NotificationSentEmailLogs();
        notificationSentEmailLogs1.setId(1L);
        NotificationSentEmailLogs notificationSentEmailLogs2 = new NotificationSentEmailLogs();
        notificationSentEmailLogs2.setId(notificationSentEmailLogs1.getId());
        assertThat(notificationSentEmailLogs1).isEqualTo(notificationSentEmailLogs2);
        notificationSentEmailLogs2.setId(2L);
        assertThat(notificationSentEmailLogs1).isNotEqualTo(notificationSentEmailLogs2);
        notificationSentEmailLogs1.setId(null);
        assertThat(notificationSentEmailLogs1).isNotEqualTo(notificationSentEmailLogs2);
    }
}
