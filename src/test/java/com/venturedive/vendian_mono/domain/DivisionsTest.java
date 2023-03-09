package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Divisions.class);
        Divisions divisions1 = new Divisions();
        divisions1.setId(1L);
        Divisions divisions2 = new Divisions();
        divisions2.setId(divisions1.getId());
        assertThat(divisions1).isEqualTo(divisions2);
        divisions2.setId(2L);
        assertThat(divisions1).isNotEqualTo(divisions2);
        divisions1.setId(null);
        assertThat(divisions1).isNotEqualTo(divisions2);
    }
}
