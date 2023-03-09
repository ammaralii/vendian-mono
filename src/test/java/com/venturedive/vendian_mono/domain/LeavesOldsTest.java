package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeavesOldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeavesOlds.class);
        LeavesOlds leavesOlds1 = new LeavesOlds();
        leavesOlds1.setId(1L);
        LeavesOlds leavesOlds2 = new LeavesOlds();
        leavesOlds2.setId(leavesOlds1.getId());
        assertThat(leavesOlds1).isEqualTo(leavesOlds2);
        leavesOlds2.setId(2L);
        assertThat(leavesOlds1).isNotEqualTo(leavesOlds2);
        leavesOlds1.setId(null);
        assertThat(leavesOlds1).isNotEqualTo(leavesOlds2);
    }
}
