package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimApproversTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimApprovers.class);
        ClaimApprovers claimApprovers1 = new ClaimApprovers();
        claimApprovers1.setId(1L);
        ClaimApprovers claimApprovers2 = new ClaimApprovers();
        claimApprovers2.setId(claimApprovers1.getId());
        assertThat(claimApprovers1).isEqualTo(claimApprovers2);
        claimApprovers2.setId(2L);
        assertThat(claimApprovers1).isNotEqualTo(claimApprovers2);
        claimApprovers1.setId(null);
        assertThat(claimApprovers1).isNotEqualTo(claimApprovers2);
    }
}
