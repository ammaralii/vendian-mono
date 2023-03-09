package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimRequestViewsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimRequestViews.class);
        ClaimRequestViews claimRequestViews1 = new ClaimRequestViews();
        claimRequestViews1.setId(1L);
        ClaimRequestViews claimRequestViews2 = new ClaimRequestViews();
        claimRequestViews2.setId(claimRequestViews1.getId());
        assertThat(claimRequestViews1).isEqualTo(claimRequestViews2);
        claimRequestViews2.setId(2L);
        assertThat(claimRequestViews1).isNotEqualTo(claimRequestViews2);
        claimRequestViews1.setId(null);
        assertThat(claimRequestViews1).isNotEqualTo(claimRequestViews2);
    }
}
