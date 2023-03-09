package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealResourceEventLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealResourceEventLogs.class);
        DealResourceEventLogs dealResourceEventLogs1 = new DealResourceEventLogs();
        dealResourceEventLogs1.setId(1L);
        DealResourceEventLogs dealResourceEventLogs2 = new DealResourceEventLogs();
        dealResourceEventLogs2.setId(dealResourceEventLogs1.getId());
        assertThat(dealResourceEventLogs1).isEqualTo(dealResourceEventLogs2);
        dealResourceEventLogs2.setId(2L);
        assertThat(dealResourceEventLogs1).isNotEqualTo(dealResourceEventLogs2);
        dealResourceEventLogs1.setId(null);
        assertThat(dealResourceEventLogs1).isNotEqualTo(dealResourceEventLogs2);
    }
}
