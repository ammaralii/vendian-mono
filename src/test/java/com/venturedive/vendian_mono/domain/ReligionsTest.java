package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReligionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Religions.class);
        Religions religions1 = new Religions();
        religions1.setId(1L);
        Religions religions2 = new Religions();
        religions2.setId(religions1.getId());
        assertThat(religions1).isEqualTo(religions2);
        religions2.setId(2L);
        assertThat(religions1).isNotEqualTo(religions2);
        religions1.setId(null);
        assertThat(religions1).isNotEqualTo(religions2);
    }
}
