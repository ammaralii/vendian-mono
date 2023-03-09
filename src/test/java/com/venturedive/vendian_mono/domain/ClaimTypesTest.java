package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimTypes.class);
        ClaimTypes claimTypes1 = new ClaimTypes();
        claimTypes1.setId(1L);
        ClaimTypes claimTypes2 = new ClaimTypes();
        claimTypes2.setId(claimTypes1.getId());
        assertThat(claimTypes1).isEqualTo(claimTypes2);
        claimTypes2.setId(2L);
        assertThat(claimTypes1).isNotEqualTo(claimTypes2);
        claimTypes1.setId(null);
        assertThat(claimTypes1).isNotEqualTo(claimTypes2);
    }
}
