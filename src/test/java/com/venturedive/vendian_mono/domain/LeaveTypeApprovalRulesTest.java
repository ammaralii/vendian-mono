package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypeApprovalRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypeApprovalRules.class);
        LeaveTypeApprovalRules leaveTypeApprovalRules1 = new LeaveTypeApprovalRules();
        leaveTypeApprovalRules1.setId(1L);
        LeaveTypeApprovalRules leaveTypeApprovalRules2 = new LeaveTypeApprovalRules();
        leaveTypeApprovalRules2.setId(leaveTypeApprovalRules1.getId());
        assertThat(leaveTypeApprovalRules1).isEqualTo(leaveTypeApprovalRules2);
        leaveTypeApprovalRules2.setId(2L);
        assertThat(leaveTypeApprovalRules1).isNotEqualTo(leaveTypeApprovalRules2);
        leaveTypeApprovalRules1.setId(null);
        assertThat(leaveTypeApprovalRules1).isNotEqualTo(leaveTypeApprovalRules2);
    }
}
