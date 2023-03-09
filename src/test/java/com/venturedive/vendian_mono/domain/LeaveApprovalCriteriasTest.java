package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveApprovalCriteriasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveApprovalCriterias.class);
        LeaveApprovalCriterias leaveApprovalCriterias1 = new LeaveApprovalCriterias();
        leaveApprovalCriterias1.setId(1L);
        LeaveApprovalCriterias leaveApprovalCriterias2 = new LeaveApprovalCriterias();
        leaveApprovalCriterias2.setId(leaveApprovalCriterias1.getId());
        assertThat(leaveApprovalCriterias1).isEqualTo(leaveApprovalCriterias2);
        leaveApprovalCriterias2.setId(2L);
        assertThat(leaveApprovalCriterias1).isNotEqualTo(leaveApprovalCriterias2);
        leaveApprovalCriterias1.setId(null);
        assertThat(leaveApprovalCriterias1).isNotEqualTo(leaveApprovalCriterias2);
    }
}
