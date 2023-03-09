package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcRatingAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcRatingAttributes.class);
        PcRatingAttributes pcRatingAttributes1 = new PcRatingAttributes();
        pcRatingAttributes1.setId(1L);
        PcRatingAttributes pcRatingAttributes2 = new PcRatingAttributes();
        pcRatingAttributes2.setId(pcRatingAttributes1.getId());
        assertThat(pcRatingAttributes1).isEqualTo(pcRatingAttributes2);
        pcRatingAttributes2.setId(2L);
        assertThat(pcRatingAttributes1).isNotEqualTo(pcRatingAttributes2);
        pcRatingAttributes1.setId(null);
        assertThat(pcRatingAttributes1).isNotEqualTo(pcRatingAttributes2);
    }
}
