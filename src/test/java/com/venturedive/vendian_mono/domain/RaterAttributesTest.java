package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaterAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaterAttributes.class);
        RaterAttributes raterAttributes1 = new RaterAttributes();
        raterAttributes1.setId(1L);
        RaterAttributes raterAttributes2 = new RaterAttributes();
        raterAttributes2.setId(raterAttributes1.getId());
        assertThat(raterAttributes1).isEqualTo(raterAttributes2);
        raterAttributes2.setId(2L);
        assertThat(raterAttributes1).isNotEqualTo(raterAttributes2);
        raterAttributes1.setId(null);
        assertThat(raterAttributes1).isNotEqualTo(raterAttributes2);
    }
}
