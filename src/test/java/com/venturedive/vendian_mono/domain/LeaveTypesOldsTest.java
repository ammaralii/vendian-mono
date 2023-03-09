package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypesOldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypesOlds.class);
        LeaveTypesOlds leaveTypesOlds1 = new LeaveTypesOlds();
        leaveTypesOlds1.setId(1L);
        LeaveTypesOlds leaveTypesOlds2 = new LeaveTypesOlds();
        leaveTypesOlds2.setId(leaveTypesOlds1.getId());
        assertThat(leaveTypesOlds1).isEqualTo(leaveTypesOlds2);
        leaveTypesOlds2.setId(2L);
        assertThat(leaveTypesOlds1).isNotEqualTo(leaveTypesOlds2);
        leaveTypesOlds1.setId(null);
        assertThat(leaveTypesOlds1).isNotEqualTo(leaveTypesOlds2);
    }
}
