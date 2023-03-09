package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaterAttributeMappingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaterAttributeMappings.class);
        RaterAttributeMappings raterAttributeMappings1 = new RaterAttributeMappings();
        raterAttributeMappings1.setId(1L);
        RaterAttributeMappings raterAttributeMappings2 = new RaterAttributeMappings();
        raterAttributeMappings2.setId(raterAttributeMappings1.getId());
        assertThat(raterAttributeMappings1).isEqualTo(raterAttributeMappings2);
        raterAttributeMappings2.setId(2L);
        assertThat(raterAttributeMappings1).isNotEqualTo(raterAttributeMappings2);
        raterAttributeMappings1.setId(null);
        assertThat(raterAttributeMappings1).isNotEqualTo(raterAttributeMappings2);
    }
}
