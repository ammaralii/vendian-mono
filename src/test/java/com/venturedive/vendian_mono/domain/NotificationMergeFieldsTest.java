package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationMergeFieldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationMergeFields.class);
        NotificationMergeFields notificationMergeFields1 = new NotificationMergeFields();
        notificationMergeFields1.setId(1L);
        NotificationMergeFields notificationMergeFields2 = new NotificationMergeFields();
        notificationMergeFields2.setId(notificationMergeFields1.getId());
        assertThat(notificationMergeFields1).isEqualTo(notificationMergeFields2);
        notificationMergeFields2.setId(2L);
        assertThat(notificationMergeFields1).isNotEqualTo(notificationMergeFields2);
        notificationMergeFields1.setId(null);
        assertThat(notificationMergeFields1).isNotEqualTo(notificationMergeFields2);
    }
}
