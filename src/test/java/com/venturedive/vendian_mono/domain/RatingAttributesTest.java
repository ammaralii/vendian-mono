package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatingAttributes.class);
        RatingAttributes ratingAttributes1 = new RatingAttributes();
        ratingAttributes1.setId(1L);
        RatingAttributes ratingAttributes2 = new RatingAttributes();
        ratingAttributes2.setId(ratingAttributes1.getId());
        assertThat(ratingAttributes1).isEqualTo(ratingAttributes2);
        ratingAttributes2.setId(2L);
        assertThat(ratingAttributes1).isNotEqualTo(ratingAttributes2);
        ratingAttributes1.setId(null);
        assertThat(ratingAttributes1).isNotEqualTo(ratingAttributes2);
    }
}
