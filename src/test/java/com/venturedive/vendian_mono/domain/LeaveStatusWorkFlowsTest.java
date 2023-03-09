package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveStatusWorkFlowsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveStatusWorkFlows.class);
        LeaveStatusWorkFlows leaveStatusWorkFlows1 = new LeaveStatusWorkFlows();
        leaveStatusWorkFlows1.setId(1L);
        LeaveStatusWorkFlows leaveStatusWorkFlows2 = new LeaveStatusWorkFlows();
        leaveStatusWorkFlows2.setId(leaveStatusWorkFlows1.getId());
        assertThat(leaveStatusWorkFlows1).isEqualTo(leaveStatusWorkFlows2);
        leaveStatusWorkFlows2.setId(2L);
        assertThat(leaveStatusWorkFlows1).isNotEqualTo(leaveStatusWorkFlows2);
        leaveStatusWorkFlows1.setId(null);
        assertThat(leaveStatusWorkFlows1).isNotEqualTo(leaveStatusWorkFlows2);
    }
}
