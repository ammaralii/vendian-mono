package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatingCategories.class);
        RatingCategories ratingCategories1 = new RatingCategories();
        ratingCategories1.setId(1L);
        RatingCategories ratingCategories2 = new RatingCategories();
        ratingCategories2.setId(ratingCategories1.getId());
        assertThat(ratingCategories1).isEqualTo(ratingCategories2);
        ratingCategories2.setId(2L);
        assertThat(ratingCategories1).isNotEqualTo(ratingCategories2);
        ratingCategories1.setId(null);
        assertThat(ratingCategories1).isNotEqualTo(ratingCategories2);
    }
}
