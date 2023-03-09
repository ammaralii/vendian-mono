package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveTypeConfigurationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypeConfigurations.class);
        LeaveTypeConfigurations leaveTypeConfigurations1 = new LeaveTypeConfigurations();
        leaveTypeConfigurations1.setId(1L);
        LeaveTypeConfigurations leaveTypeConfigurations2 = new LeaveTypeConfigurations();
        leaveTypeConfigurations2.setId(leaveTypeConfigurations1.getId());
        assertThat(leaveTypeConfigurations1).isEqualTo(leaveTypeConfigurations2);
        leaveTypeConfigurations2.setId(2L);
        assertThat(leaveTypeConfigurations1).isNotEqualTo(leaveTypeConfigurations2);
        leaveTypeConfigurations1.setId(null);
        assertThat(leaveTypeConfigurations1).isNotEqualTo(leaveTypeConfigurations2);
    }
}
