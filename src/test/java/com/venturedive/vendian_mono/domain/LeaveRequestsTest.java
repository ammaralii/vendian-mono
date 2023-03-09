package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveRequestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRequests.class);
        LeaveRequests leaveRequests1 = new LeaveRequests();
        leaveRequests1.setId(1L);
        LeaveRequests leaveRequests2 = new LeaveRequests();
        leaveRequests2.setId(leaveRequests1.getId());
        assertThat(leaveRequests1).isEqualTo(leaveRequests2);
        leaveRequests2.setId(2L);
        assertThat(leaveRequests1).isNotEqualTo(leaveRequests2);
        leaveRequests1.setId(null);
        assertThat(leaveRequests1).isNotEqualTo(leaveRequests2);
    }
}
