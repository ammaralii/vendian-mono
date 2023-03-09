package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypes.class);
        LeaveTypes leaveTypes1 = new LeaveTypes();
        leaveTypes1.setId(1L);
        LeaveTypes leaveTypes2 = new LeaveTypes();
        leaveTypes2.setId(leaveTypes1.getId());
        assertThat(leaveTypes1).isEqualTo(leaveTypes2);
        leaveTypes2.setId(2L);
        assertThat(leaveTypes1).isNotEqualTo(leaveTypes2);
        leaveTypes1.setId(null);
        assertThat(leaveTypes1).isNotEqualTo(leaveTypes2);
    }
}
