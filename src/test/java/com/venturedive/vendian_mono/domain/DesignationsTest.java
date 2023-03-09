package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesignationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designations.class);
        Designations designations1 = new Designations();
        designations1.setId(1L);
        Designations designations2 = new Designations();
        designations2.setId(designations1.getId());
        assertThat(designations1).isEqualTo(designations2);
        designations2.setId(2L);
        assertThat(designations1).isNotEqualTo(designations2);
        designations1.setId(null);
        assertThat(designations1).isNotEqualTo(designations2);
    }
}
