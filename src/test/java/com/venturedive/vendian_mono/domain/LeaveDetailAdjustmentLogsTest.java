package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveDetailAdjustmentLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveDetailAdjustmentLogs.class);
        LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs1 = new LeaveDetailAdjustmentLogs();
        leaveDetailAdjustmentLogs1.setId(1L);
        LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs2 = new LeaveDetailAdjustmentLogs();
        leaveDetailAdjustmentLogs2.setId(leaveDetailAdjustmentLogs1.getId());
        assertThat(leaveDetailAdjustmentLogs1).isEqualTo(leaveDetailAdjustmentLogs2);
        leaveDetailAdjustmentLogs2.setId(2L);
        assertThat(leaveDetailAdjustmentLogs1).isNotEqualTo(leaveDetailAdjustmentLogs2);
        leaveDetailAdjustmentLogs1.setId(null);
        assertThat(leaveDetailAdjustmentLogs1).isNotEqualTo(leaveDetailAdjustmentLogs2);
    }
}
