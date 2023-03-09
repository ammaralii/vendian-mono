package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeavesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leaves.class);
        Leaves leaves1 = new Leaves();
        leaves1.setId(1L);
        Leaves leaves2 = new Leaves();
        leaves2.setId(leaves1.getId());
        assertThat(leaves1).isEqualTo(leaves2);
        leaves2.setId(2L);
        assertThat(leaves1).isNotEqualTo(leaves2);
        leaves1.setId(null);
        assertThat(leaves1).isNotEqualTo(leaves2);
    }
}
