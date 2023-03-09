package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveRequestApproverFlowsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRequestApproverFlows.class);
        LeaveRequestApproverFlows leaveRequestApproverFlows1 = new LeaveRequestApproverFlows();
        leaveRequestApproverFlows1.setId(1L);
        LeaveRequestApproverFlows leaveRequestApproverFlows2 = new LeaveRequestApproverFlows();
        leaveRequestApproverFlows2.setId(leaveRequestApproverFlows1.getId());
        assertThat(leaveRequestApproverFlows1).isEqualTo(leaveRequestApproverFlows2);
        leaveRequestApproverFlows2.setId(2L);
        assertThat(leaveRequestApproverFlows1).isNotEqualTo(leaveRequestApproverFlows2);
        leaveRequestApproverFlows1.setId(null);
        assertThat(leaveRequestApproverFlows1).isNotEqualTo(leaveRequestApproverFlows2);
    }
}
