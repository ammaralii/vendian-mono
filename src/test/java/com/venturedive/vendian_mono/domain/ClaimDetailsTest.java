package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimDetails.class);
        ClaimDetails claimDetails1 = new ClaimDetails();
        claimDetails1.setId(1L);
        ClaimDetails claimDetails2 = new ClaimDetails();
        claimDetails2.setId(claimDetails1.getId());
        assertThat(claimDetails1).isEqualTo(claimDetails2);
        claimDetails2.setId(2L);
        assertThat(claimDetails1).isNotEqualTo(claimDetails2);
        claimDetails1.setId(null);
        assertThat(claimDetails1).isNotEqualTo(claimDetails2);
    }
}
