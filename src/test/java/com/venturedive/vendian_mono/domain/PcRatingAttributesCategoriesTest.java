package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcRatingAttributesCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcRatingAttributesCategories.class);
        PcRatingAttributesCategories pcRatingAttributesCategories1 = new PcRatingAttributesCategories();
        pcRatingAttributesCategories1.setId(1L);
        PcRatingAttributesCategories pcRatingAttributesCategories2 = new PcRatingAttributesCategories();
        pcRatingAttributesCategories2.setId(pcRatingAttributesCategories1.getId());
        assertThat(pcRatingAttributesCategories1).isEqualTo(pcRatingAttributesCategories2);
        pcRatingAttributesCategories2.setId(2L);
        assertThat(pcRatingAttributesCategories1).isNotEqualTo(pcRatingAttributesCategories2);
        pcRatingAttributesCategories1.setId(null);
        assertThat(pcRatingAttributesCategories1).isNotEqualTo(pcRatingAttributesCategories2);
    }
}
