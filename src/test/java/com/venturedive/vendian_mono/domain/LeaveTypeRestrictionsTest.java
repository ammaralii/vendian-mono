package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypeRestrictionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypeRestrictions.class);
        LeaveTypeRestrictions leaveTypeRestrictions1 = new LeaveTypeRestrictions();
        leaveTypeRestrictions1.setId(1L);
        LeaveTypeRestrictions leaveTypeRestrictions2 = new LeaveTypeRestrictions();
        leaveTypeRestrictions2.setId(leaveTypeRestrictions1.getId());
        assertThat(leaveTypeRestrictions1).isEqualTo(leaveTypeRestrictions2);
        leaveTypeRestrictions2.setId(2L);
        assertThat(leaveTypeRestrictions1).isNotEqualTo(leaveTypeRestrictions2);
        leaveTypeRestrictions1.setId(null);
        assertThat(leaveTypeRestrictions1).isNotEqualTo(leaveTypeRestrictions2);
    }
}
