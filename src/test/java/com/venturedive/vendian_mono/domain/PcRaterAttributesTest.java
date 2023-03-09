package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcRaterAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcRaterAttributes.class);
        PcRaterAttributes pcRaterAttributes1 = new PcRaterAttributes();
        pcRaterAttributes1.setId(1L);
        PcRaterAttributes pcRaterAttributes2 = new PcRaterAttributes();
        pcRaterAttributes2.setId(pcRaterAttributes1.getId());
        assertThat(pcRaterAttributes1).isEqualTo(pcRaterAttributes2);
        pcRaterAttributes2.setId(2L);
        assertThat(pcRaterAttributes1).isNotEqualTo(pcRaterAttributes2);
        pcRaterAttributes1.setId(null);
        assertThat(pcRaterAttributes1).isNotEqualTo(pcRaterAttributes2);
    }
}
