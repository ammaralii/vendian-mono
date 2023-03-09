package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealEventsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealEvents.class);
        DealEvents dealEvents1 = new DealEvents();
        dealEvents1.setId(1L);
        DealEvents dealEvents2 = new DealEvents();
        dealEvents2.setId(dealEvents1.getId());
        assertThat(dealEvents1).isEqualTo(dealEvents2);
        dealEvents2.setId(2L);
        assertThat(dealEvents1).isNotEqualTo(dealEvents2);
        dealEvents1.setId(null);
        assertThat(dealEvents1).isNotEqualTo(dealEvents2);
    }
}
