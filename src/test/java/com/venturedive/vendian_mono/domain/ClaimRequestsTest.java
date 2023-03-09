package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimRequestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimRequests.class);
        ClaimRequests claimRequests1 = new ClaimRequests();
        claimRequests1.setId(1L);
        ClaimRequests claimRequests2 = new ClaimRequests();
        claimRequests2.setId(claimRequests1.getId());
        assertThat(claimRequests1).isEqualTo(claimRequests2);
        claimRequests2.setId(2L);
        assertThat(claimRequests1).isNotEqualTo(claimRequests2);
        claimRequests1.setId(null);
        assertThat(claimRequests1).isNotEqualTo(claimRequests2);
    }
}
