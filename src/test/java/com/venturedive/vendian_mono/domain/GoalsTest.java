package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoalsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goals.class);
        Goals goals1 = new Goals();
        goals1.setId(1L);
        Goals goals2 = new Goals();
        goals2.setId(goals1.getId());
        assertThat(goals1).isEqualTo(goals2);
        goals2.setId(2L);
        assertThat(goals1).isNotEqualTo(goals2);
        goals1.setId(null);
        assertThat(goals1).isNotEqualTo(goals2);
    }
}
