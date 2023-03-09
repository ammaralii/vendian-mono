package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveDetails.class);
        LeaveDetails leaveDetails1 = new LeaveDetails();
        leaveDetails1.setId(1L);
        LeaveDetails leaveDetails2 = new LeaveDetails();
        leaveDetails2.setId(leaveDetails1.getId());
        assertThat(leaveDetails1).isEqualTo(leaveDetails2);
        leaveDetails2.setId(2L);
        assertThat(leaveDetails1).isNotEqualTo(leaveDetails2);
        leaveDetails1.setId(null);
        assertThat(leaveDetails1).isNotEqualTo(leaveDetails2);
    }
}
