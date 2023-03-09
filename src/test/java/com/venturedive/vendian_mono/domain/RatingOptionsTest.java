package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingOptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatingOptions.class);
        RatingOptions ratingOptions1 = new RatingOptions();
        ratingOptions1.setId(1L);
        RatingOptions ratingOptions2 = new RatingOptions();
        ratingOptions2.setId(ratingOptions1.getId());
        assertThat(ratingOptions1).isEqualTo(ratingOptions2);
        ratingOptions2.setId(2L);
        assertThat(ratingOptions1).isNotEqualTo(ratingOptions2);
        ratingOptions1.setId(null);
        assertThat(ratingOptions1).isNotEqualTo(ratingOptions2);
    }
}
