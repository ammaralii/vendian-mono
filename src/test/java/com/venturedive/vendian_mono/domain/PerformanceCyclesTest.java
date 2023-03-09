package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceCyclesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceCycles.class);
        PerformanceCycles performanceCycles1 = new PerformanceCycles();
        performanceCycles1.setId(1L);
        PerformanceCycles performanceCycles2 = new PerformanceCycles();
        performanceCycles2.setId(performanceCycles1.getId());
        assertThat(performanceCycles1).isEqualTo(performanceCycles2);
        performanceCycles2.setId(2L);
        assertThat(performanceCycles1).isNotEqualTo(performanceCycles2);
        performanceCycles1.setId(null);
        assertThat(performanceCycles1).isNotEqualTo(performanceCycles2);
    }
}
