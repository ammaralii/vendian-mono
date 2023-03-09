package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeavesCopyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeavesCopy.class);
        LeavesCopy leavesCopy1 = new LeavesCopy();
        leavesCopy1.setId(1L);
        LeavesCopy leavesCopy2 = new LeavesCopy();
        leavesCopy2.setId(leavesCopy1.getId());
        assertThat(leavesCopy1).isEqualTo(leavesCopy2);
        leavesCopy2.setId(2L);
        assertThat(leavesCopy1).isNotEqualTo(leavesCopy2);
        leavesCopy1.setId(null);
        assertThat(leavesCopy1).isNotEqualTo(leavesCopy2);
    }
}
