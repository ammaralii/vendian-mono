package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveStatusesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveStatuses.class);
        LeaveStatuses leaveStatuses1 = new LeaveStatuses();
        leaveStatuses1.setId(1L);
        LeaveStatuses leaveStatuses2 = new LeaveStatuses();
        leaveStatuses2.setId(leaveStatuses1.getId());
        assertThat(leaveStatuses1).isEqualTo(leaveStatuses2);
        leaveStatuses2.setId(2L);
        assertThat(leaveStatuses1).isNotEqualTo(leaveStatuses2);
        leaveStatuses1.setId(null);
        assertThat(leaveStatuses1).isNotEqualTo(leaveStatuses2);
    }
}
