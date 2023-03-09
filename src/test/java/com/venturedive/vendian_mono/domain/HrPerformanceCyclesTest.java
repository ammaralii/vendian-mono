package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HrPerformanceCyclesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HrPerformanceCycles.class);
        HrPerformanceCycles hrPerformanceCycles1 = new HrPerformanceCycles();
        hrPerformanceCycles1.setId(1L);
        HrPerformanceCycles hrPerformanceCycles2 = new HrPerformanceCycles();
        hrPerformanceCycles2.setId(hrPerformanceCycles1.getId());
        assertThat(hrPerformanceCycles1).isEqualTo(hrPerformanceCycles2);
        hrPerformanceCycles2.setId(2L);
        assertThat(hrPerformanceCycles1).isNotEqualTo(hrPerformanceCycles2);
        hrPerformanceCycles1.setId(null);
        assertThat(hrPerformanceCycles1).isNotEqualTo(hrPerformanceCycles2);
    }
}
