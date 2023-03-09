package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceCycles20190826Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceCycles20190826.class);
        PerformanceCycles20190826 performanceCycles201908261 = new PerformanceCycles20190826();
        performanceCycles201908261.setId(1L);
        PerformanceCycles20190826 performanceCycles201908262 = new PerformanceCycles20190826();
        performanceCycles201908262.setId(performanceCycles201908261.getId());
        assertThat(performanceCycles201908261).isEqualTo(performanceCycles201908262);
        performanceCycles201908262.setId(2L);
        assertThat(performanceCycles201908261).isNotEqualTo(performanceCycles201908262);
        performanceCycles201908261.setId(null);
        assertThat(performanceCycles201908261).isNotEqualTo(performanceCycles201908262);
    }
}
