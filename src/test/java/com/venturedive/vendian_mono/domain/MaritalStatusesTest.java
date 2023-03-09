package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaritalStatusesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaritalStatuses.class);
        MaritalStatuses maritalStatuses1 = new MaritalStatuses();
        maritalStatuses1.setId(1L);
        MaritalStatuses maritalStatuses2 = new MaritalStatuses();
        maritalStatuses2.setId(maritalStatuses1.getId());
        assertThat(maritalStatuses1).isEqualTo(maritalStatuses2);
        maritalStatuses2.setId(2L);
        assertThat(maritalStatuses1).isNotEqualTo(maritalStatuses2);
        maritalStatuses1.setId(null);
        assertThat(maritalStatuses1).isNotEqualTo(maritalStatuses2);
    }
}
