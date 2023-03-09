package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveEscalationApproversTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveEscalationApprovers.class);
        LeaveEscalationApprovers leaveEscalationApprovers1 = new LeaveEscalationApprovers();
        leaveEscalationApprovers1.setId(1L);
        LeaveEscalationApprovers leaveEscalationApprovers2 = new LeaveEscalationApprovers();
        leaveEscalationApprovers2.setId(leaveEscalationApprovers1.getId());
        assertThat(leaveEscalationApprovers1).isEqualTo(leaveEscalationApprovers2);
        leaveEscalationApprovers2.setId(2L);
        assertThat(leaveEscalationApprovers1).isNotEqualTo(leaveEscalationApprovers2);
        leaveEscalationApprovers1.setId(null);
        assertThat(leaveEscalationApprovers1).isNotEqualTo(leaveEscalationApprovers2);
    }
}
