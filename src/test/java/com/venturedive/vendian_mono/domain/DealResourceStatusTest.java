package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealResourceStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealResourceStatus.class);
        DealResourceStatus dealResourceStatus1 = new DealResourceStatus();
        dealResourceStatus1.setId(1L);
        DealResourceStatus dealResourceStatus2 = new DealResourceStatus();
        dealResourceStatus2.setId(dealResourceStatus1.getId());
        assertThat(dealResourceStatus1).isEqualTo(dealResourceStatus2);
        dealResourceStatus2.setId(2L);
        assertThat(dealResourceStatus1).isNotEqualTo(dealResourceStatus2);
        dealResourceStatus1.setId(null);
        assertThat(dealResourceStatus1).isNotEqualTo(dealResourceStatus2);
    }
}
