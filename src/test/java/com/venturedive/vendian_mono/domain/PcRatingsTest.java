package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcRatingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcRatings.class);
        PcRatings pcRatings1 = new PcRatings();
        pcRatings1.setId(1L);
        PcRatings pcRatings2 = new PcRatings();
        pcRatings2.setId(pcRatings1.getId());
        assertThat(pcRatings1).isEqualTo(pcRatings2);
        pcRatings2.setId(2L);
        assertThat(pcRatings1).isNotEqualTo(pcRatings2);
        pcRatings1.setId(null);
        assertThat(pcRatings1).isNotEqualTo(pcRatings2);
    }
}
