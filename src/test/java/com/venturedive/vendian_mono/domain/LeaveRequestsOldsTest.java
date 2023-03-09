package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveRequestsOldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRequestsOlds.class);
        LeaveRequestsOlds leaveRequestsOlds1 = new LeaveRequestsOlds();
        leaveRequestsOlds1.setId(1L);
        LeaveRequestsOlds leaveRequestsOlds2 = new LeaveRequestsOlds();
        leaveRequestsOlds2.setId(leaveRequestsOlds1.getId());
        assertThat(leaveRequestsOlds1).isEqualTo(leaveRequestsOlds2);
        leaveRequestsOlds2.setId(2L);
        assertThat(leaveRequestsOlds1).isNotEqualTo(leaveRequestsOlds2);
        leaveRequestsOlds1.setId(null);
        assertThat(leaveRequestsOlds1).isNotEqualTo(leaveRequestsOlds2);
    }
}
