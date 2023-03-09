package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveRequestApproversTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRequestApprovers.class);
        LeaveRequestApprovers leaveRequestApprovers1 = new LeaveRequestApprovers();
        leaveRequestApprovers1.setId(1L);
        LeaveRequestApprovers leaveRequestApprovers2 = new LeaveRequestApprovers();
        leaveRequestApprovers2.setId(leaveRequestApprovers1.getId());
        assertThat(leaveRequestApprovers1).isEqualTo(leaveRequestApprovers2);
        leaveRequestApprovers2.setId(2L);
        assertThat(leaveRequestApprovers1).isNotEqualTo(leaveRequestApprovers2);
        leaveRequestApprovers1.setId(null);
        assertThat(leaveRequestApprovers1).isNotEqualTo(leaveRequestApprovers2);
    }
}
