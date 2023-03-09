package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveApproversTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveApprovers.class);
        LeaveApprovers leaveApprovers1 = new LeaveApprovers();
        leaveApprovers1.setId(1L);
        LeaveApprovers leaveApprovers2 = new LeaveApprovers();
        leaveApprovers2.setId(leaveApprovers1.getId());
        assertThat(leaveApprovers1).isEqualTo(leaveApprovers2);
        leaveApprovers2.setId(2L);
        assertThat(leaveApprovers1).isNotEqualTo(leaveApprovers2);
        leaveApprovers1.setId(null);
        assertThat(leaveApprovers1).isNotEqualTo(leaveApprovers2);
    }
}
