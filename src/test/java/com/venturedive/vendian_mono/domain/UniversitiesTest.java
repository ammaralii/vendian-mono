package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UniversitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Universities.class);
        Universities universities1 = new Universities();
        universities1.setId(1L);
        Universities universities2 = new Universities();
        universities2.setId(universities1.getId());
        assertThat(universities1).isEqualTo(universities2);
        universities2.setId(2L);
        assertThat(universities1).isNotEqualTo(universities2);
        universities1.setId(null);
        assertThat(universities1).isNotEqualTo(universities2);
    }
}
