package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationEventsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationEvents.class);
        NotificationEvents notificationEvents1 = new NotificationEvents();
        notificationEvents1.setId(1L);
        NotificationEvents notificationEvents2 = new NotificationEvents();
        notificationEvents2.setId(notificationEvents1.getId());
        assertThat(notificationEvents1).isEqualTo(notificationEvents2);
        notificationEvents2.setId(2L);
        assertThat(notificationEvents1).isNotEqualTo(notificationEvents2);
        notificationEvents1.setId(null);
        assertThat(notificationEvents1).isNotEqualTo(notificationEvents2);
    }
}
