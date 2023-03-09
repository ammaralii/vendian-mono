package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZLeaveRequestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZLeaveRequests.class);
        ZLeaveRequests zLeaveRequests1 = new ZLeaveRequests();
        zLeaveRequests1.setId(1L);
        ZLeaveRequests zLeaveRequests2 = new ZLeaveRequests();
        zLeaveRequests2.setId(zLeaveRequests1.getId());
        assertThat(zLeaveRequests1).isEqualTo(zLeaveRequests2);
        zLeaveRequests2.setId(2L);
        assertThat(zLeaveRequests1).isNotEqualTo(zLeaveRequests2);
        zLeaveRequests1.setId(null);
        assertThat(zLeaveRequests1).isNotEqualTo(zLeaveRequests2);
    }
}
