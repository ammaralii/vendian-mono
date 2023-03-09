package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimImagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimImages.class);
        ClaimImages claimImages1 = new ClaimImages();
        claimImages1.setId(1L);
        ClaimImages claimImages2 = new ClaimImages();
        claimImages2.setId(claimImages1.getId());
        assertThat(claimImages1).isEqualTo(claimImages2);
        claimImages2.setId(2L);
        assertThat(claimImages1).isNotEqualTo(claimImages2);
        claimImages1.setId(null);
        assertThat(claimImages1).isNotEqualTo(claimImages2);
    }
}
