package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequelizemetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sequelizemeta.class);
        Sequelizemeta sequelizemeta1 = new Sequelizemeta();
        sequelizemeta1.setId(1L);
        Sequelizemeta sequelizemeta2 = new Sequelizemeta();
        sequelizemeta2.setId(sequelizemeta1.getId());
        assertThat(sequelizemeta1).isEqualTo(sequelizemeta2);
        sequelizemeta2.setId(2L);
        assertThat(sequelizemeta1).isNotEqualTo(sequelizemeta2);
        sequelizemeta1.setId(null);
        assertThat(sequelizemeta1).isNotEqualTo(sequelizemeta2);
    }
}
