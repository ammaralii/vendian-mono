package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingAttributeMappingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatingAttributeMappings.class);
        RatingAttributeMappings ratingAttributeMappings1 = new RatingAttributeMappings();
        ratingAttributeMappings1.setId(1L);
        RatingAttributeMappings ratingAttributeMappings2 = new RatingAttributeMappings();
        ratingAttributeMappings2.setId(ratingAttributeMappings1.getId());
        assertThat(ratingAttributeMappings1).isEqualTo(ratingAttributeMappings2);
        ratingAttributeMappings2.setId(2L);
        assertThat(ratingAttributeMappings1).isNotEqualTo(ratingAttributeMappings2);
        ratingAttributeMappings1.setId(null);
        assertThat(ratingAttributeMappings1).isNotEqualTo(ratingAttributeMappings2);
    }
}
