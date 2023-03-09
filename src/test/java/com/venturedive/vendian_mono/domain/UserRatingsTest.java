package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRatingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRatings.class);
        UserRatings userRatings1 = new UserRatings();
        userRatings1.setId(1L);
        UserRatings userRatings2 = new UserRatings();
        userRatings2.setId(userRatings1.getId());
        assertThat(userRatings1).isEqualTo(userRatings2);
        userRatings2.setId(2L);
        assertThat(userRatings1).isNotEqualTo(userRatings2);
        userRatings1.setId(null);
        assertThat(userRatings1).isNotEqualTo(userRatings2);
    }
}
