package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTemplatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationTemplates.class);
        NotificationTemplates notificationTemplates1 = new NotificationTemplates();
        notificationTemplates1.setId(1L);
        NotificationTemplates notificationTemplates2 = new NotificationTemplates();
        notificationTemplates2.setId(notificationTemplates1.getId());
        assertThat(notificationTemplates1).isEqualTo(notificationTemplates2);
        notificationTemplates2.setId(2L);
        assertThat(notificationTemplates1).isNotEqualTo(notificationTemplates2);
        notificationTemplates1.setId(null);
        assertThat(notificationTemplates1).isNotEqualTo(notificationTemplates2);
    }
}
