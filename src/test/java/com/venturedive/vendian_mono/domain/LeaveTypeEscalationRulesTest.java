package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypeEscalationRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypeEscalationRules.class);
        LeaveTypeEscalationRules leaveTypeEscalationRules1 = new LeaveTypeEscalationRules();
        leaveTypeEscalationRules1.setId(1L);
        LeaveTypeEscalationRules leaveTypeEscalationRules2 = new LeaveTypeEscalationRules();
        leaveTypeEscalationRules2.setId(leaveTypeEscalationRules1.getId());
        assertThat(leaveTypeEscalationRules1).isEqualTo(leaveTypeEscalationRules2);
        leaveTypeEscalationRules2.setId(2L);
        assertThat(leaveTypeEscalationRules1).isNotEqualTo(leaveTypeEscalationRules2);
        leaveTypeEscalationRules1.setId(null);
        assertThat(leaveTypeEscalationRules1).isNotEqualTo(leaveTypeEscalationRules2);
    }
}
